package com.safety.womensafetyapp.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Locale

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun registerUser(email: String, password: String, usernameRaw: String, phone: String?, age: String?, location: String?) {
        val username = usernameRaw.lowercase(Locale.ROOT)
        require(username.matches(Regex("^[a-z0-9._-]{3,20}$")))
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: error("No UID")
        try {
            // Set Firebase Auth display name so it shows up under Authentication
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
            )?.await()

            db.runTransaction { tx ->
                val unameRef = db.collection("usernames").document(username)
                val userRef = db.collection("users").document(uid)
                if (tx.get(unameRef).exists()) throw IllegalStateException("Username taken")
                tx.set(unameRef, mapOf("uid" to uid, "email" to email))
                tx.set(
                    userRef,
                    mapOf(
                        "username" to username,
                        "email" to email,
                        "phone" to (phone ?: ""),
                        // Store age as number if possible, else store as string
                        "age" to (age?.toIntOrNull() ?: age ?: ""),
                        "location" to (location ?: ""),
                        "createdAt" to FieldValue.serverTimestamp(),
                        "lastLogin" to FieldValue.serverTimestamp()
                    )
                )
                null
            }.await()
        } catch (e: Exception) {
            auth.currentUser?.delete()?.await()
            auth.signOut()
            throw e
        }
    }

    suspend fun signInWithUsername(usernameRaw: String, password: String) {
        val username = usernameRaw.lowercase(Locale.ROOT)
        val unameSnap = db.collection("usernames").document(username).get().await()
        if (!unameSnap.exists()) error("User not found")
        val email = unameSnap.getString("email") ?: error("Missing email")
        val uid = unameSnap.getString("uid") ?: error("Missing uid")
        auth.signInWithEmailAndPassword(email, password).await()
        db.collection("users").document(uid)
            .update("lastLogin", FieldValue.serverTimestamp())
            .await()
    }
}
