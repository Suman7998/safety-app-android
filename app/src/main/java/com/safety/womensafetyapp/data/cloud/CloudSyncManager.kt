package com.safety.womensafetyapp.data.cloud

import com.google.firebase.firestore.FirebaseFirestore
import com.safety.womensafetyapp.BuildConfig
import com.safety.womensafetyapp.data.AppDatabase
import com.safety.womensafetyapp.data.model.*
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.tasks.await
import android.content.Context
import com.google.firebase.FirebaseApp

class CloudSyncManager(private val appContext: Context, private val db: AppDatabase) {

    private fun firestore(): FirebaseFirestore {
        if (!BuildConfig.CLOUD_SYNC_ENABLED) {
            throw IllegalStateException("Cloud sync is disabled. Enable BuildConfig.CLOUD_SYNC_ENABLED to use it.")
        }
        ensureFirebase()
        return FirebaseFirestore.getInstance()
    }

    private fun ensureFirebase() {
        if (FirebaseApp.getApps(appContext).isEmpty()) {
            // Initialize using google-services.json auto-configured defaults
            FirebaseApp.initializeApp(appContext)
        }
    }

    // Push all local data to Firestore (basic upsert by id)
    suspend fun pushAll() {
        val fs = firestore()

        // Users
        db.query(SimpleSQLiteQuery("SELECT id, username, email, phone FROM users"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getString(c.getColumnIndexOrThrow("id"))
                    val data = mapOf(
                        "username" to c.getString(c.getColumnIndexOrThrow("username")),
                        "email" to c.getString(c.getColumnIndexOrThrow("email")),
                        "phone" to c.getString(c.getColumnIndexOrThrow("phone"))
                    )
                    fs.collection("users").document(id).set(data).await()
                }
            }

        // Safe zones
        db.query(SimpleSQLiteQuery("SELECT id, name, type, latitude, longitude, address, phone, isVerified, addedBy FROM safe_zones"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndexOrThrow("id")).toString()
                    val data = mapOf(
                        "name" to c.getString(c.getColumnIndexOrThrow("name")),
                        "type" to c.getString(c.getColumnIndexOrThrow("type")),
                        "latitude" to c.getDouble(c.getColumnIndexOrThrow("latitude")),
                        "longitude" to c.getDouble(c.getColumnIndexOrThrow("longitude")),
                        "address" to c.getString(c.getColumnIndexOrThrow("address")),
                        "phone" to c.getString(c.getColumnIndexOrThrow("phone")),
                        "isVerified" to (c.getInt(c.getColumnIndexOrThrow("isVerified")) == 1),
                        "addedBy" to c.getString(c.getColumnIndexOrThrow("addedBy"))
                    )
                    fs.collection("safe_zones").document(id).set(data).await()
                }
            }

        // Unsafe zones
        db.query(SimpleSQLiteQuery("SELECT id, latitude, longitude, address, reportedBy, reportDate, description, dangerLevel, isVerified FROM unsafe_zones"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndexOrThrow("id")).toString()
                    val data = mapOf(
                        "latitude" to c.getDouble(c.getColumnIndexOrThrow("latitude")),
                        "longitude" to c.getDouble(c.getColumnIndexOrThrow("longitude")),
                        "address" to c.getString(c.getColumnIndexOrThrow("address")),
                        "reportedBy" to c.getString(c.getColumnIndexOrThrow("reportedBy")),
                        "reportDate" to c.getLong(c.getColumnIndexOrThrow("reportDate")),
                        "description" to c.getString(c.getColumnIndexOrThrow("description")),
                        "dangerLevel" to c.getString(c.getColumnIndexOrThrow("dangerLevel")),
                        "isVerified" to (c.getInt(c.getColumnIndexOrThrow("isVerified")) == 1)
                    )
                    fs.collection("unsafe_zones").document(id).set(data).await()
                }
            }

        // Emergency contacts
        db.query(SimpleSQLiteQuery("SELECT id, userId, name, phone, email, relation, isPrimary FROM emergency_contacts"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndexOrThrow("id")).toString()
                    val data = mapOf(
                        "userId" to c.getString(c.getColumnIndexOrThrow("userId")),
                        "name" to c.getString(c.getColumnIndexOrThrow("name")),
                        "phone" to c.getString(c.getColumnIndexOrThrow("phone")),
                        "email" to c.getString(c.getColumnIndexOrThrow("email")),
                        "relation" to c.getString(c.getColumnIndexOrThrow("relation")),
                        "isPrimary" to (c.getInt(c.getColumnIndexOrThrow("isPrimary")) == 1)
                    )
                    fs.collection("emergency_contacts").document(id).set(data).await()
                }
            }

        // Message logs (omit recipients to avoid parsing converter text; other fields included)
        db.query(SimpleSQLiteQuery("SELECT id, userId, messageType, timestamp, content, status, latitude, longitude, locationName FROM message_logs"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndexOrThrow("id")).toString()
                    val data = mapOf(
                        "userId" to c.getString(c.getColumnIndexOrThrow("userId")),
                        "messageType" to c.getString(c.getColumnIndexOrThrow("messageType")),
                        "timestamp" to c.getLong(c.getColumnIndexOrThrow("timestamp")),
                        "content" to c.getString(c.getColumnIndexOrThrow("content")),
                        "status" to c.getString(c.getColumnIndexOrThrow("status")),
                        "latitude" to (if (c.isNull(c.getColumnIndexOrThrow("latitude"))) null else c.getDouble(c.getColumnIndexOrThrow("latitude"))),
                        "longitude" to (if (c.isNull(c.getColumnIndexOrThrow("longitude"))) null else c.getDouble(c.getColumnIndexOrThrow("longitude"))),
                        "locationName" to c.getString(c.getColumnIndexOrThrow("locationName"))
                    )
                    fs.collection("message_logs").document(id).set(data).await()
                }
            }

        // Guardian acks
        db.query(SimpleSQLiteQuery("SELECT id, messageId, guardianId, ackTime, status, message FROM guardian_acks"))
            .use { c ->
                while (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndexOrThrow("id")).toString()
                    val data = mapOf(
                        "messageId" to c.getLong(c.getColumnIndexOrThrow("messageId")),
                        "guardianId" to c.getLong(c.getColumnIndexOrThrow("guardianId")),
                        "ackTime" to c.getLong(c.getColumnIndexOrThrow("ackTime")),
                        "status" to c.getString(c.getColumnIndexOrThrow("status")),
                        "message" to c.getString(c.getColumnIndexOrThrow("message"))
                    )
                    fs.collection("guardian_acks").document(id).set(data).await()
                }
            }
    }

    // Pull Firestore data and upsert locally (limited scope basic upsert)
    suspend fun pullAll() {
        val fs = firestore()

        // Users
        fs.collection("users").get().await().documents.forEach { d ->
            val user = User(
                id = d.id,
                username = d.getString("username") ?: d.id,
                email = d.getString("email"),
                phone = d.getString("phone") ?: ""
            )
            db.userDao().insert(user)
        }

        // Safe zones
        fs.collection("safe_zones").get().await().documents.forEach { d ->
            val sz = SafeZone(
                id = d.id.toLongOrNull() ?: 0L,
                name = d.getString("name") ?: "",
                type = SafeZone.ZoneType.valueOf(d.getString("type") ?: SafeZone.ZoneType.OTHER.name),
                latitude = d.getDouble("latitude") ?: 0.0,
                longitude = d.getDouble("longitude") ?: 0.0,
                address = d.getString("address") ?: "",
                phone = d.getString("phone"),
                isVerified = d.getBoolean("isVerified") ?: false,
                addedBy = d.getString("addedBy") ?: "system"
            )
            db.safeZoneDao().insert(sz)
        }

        // Unsafe zones
        fs.collection("unsafe_zones").get().await().documents.forEach { d ->
            val uz = UnsafeZone(
                id = d.id.toLongOrNull() ?: 0L,
                latitude = d.getDouble("latitude") ?: 0.0,
                longitude = d.getDouble("longitude") ?: 0.0,
                address = d.getString("address") ?: "",
                reportedBy = d.getString("reportedBy") ?: "",
                reportDate = d.getLong("reportDate") ?: 0L,
                description = d.getString("description"),
                dangerLevel = UnsafeZone.DangerLevel.valueOf(d.getString("dangerLevel") ?: UnsafeZone.DangerLevel.LOW.name),
                isVerified = d.getBoolean("isVerified") ?: false
            )
            db.unsafeZoneDao().insert(uz)
        }

        // Emergency contacts
        fs.collection("emergency_contacts").get().await().documents.forEach { d ->
            val ec = EmergencyContact(
                id = d.id.toLongOrNull() ?: 0L,
                userId = d.getString("userId") ?: "",
                name = d.getString("name") ?: "",
                phone = d.getString("phone") ?: "",
                email = d.getString("email"),
                relation = d.getString("relation"),
                isPrimary = d.getBoolean("isPrimary") ?: false
            )
            db.emergencyContactDao().insert(ec)
        }

        // Message logs
        fs.collection("message_logs").get().await().documents.forEach { d ->
            val ml = MessageLog(
                id = d.id.toLongOrNull() ?: 0L,
                userId = d.getString("userId") ?: "",
                messageType = MessageLog.MessageType.valueOf(d.getString("messageType") ?: MessageLog.MessageType.SOS.name),
                timestamp = d.getLong("timestamp") ?: 0L,
                content = d.getString("content") ?: "",
                recipients = (d.get("recipients") as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                status = MessageLog.MessageStatus.valueOf(d.getString("status") ?: MessageLog.MessageStatus.SENT.name),
                latitude = d.getDouble("latitude"),
                longitude = d.getDouble("longitude"),
                locationName = d.getString("locationName")
            )
            db.messageLogDao().insert(ml)
        }

        // Guardian acks
        fs.collection("guardian_acks").get().await().documents.forEach { d ->
            val ga = GuardianAck(
                id = d.id.toLongOrNull() ?: 0L,
                messageId = (d.getLong("messageId") ?: 0L),
                guardianId = (d.getLong("guardianId") ?: 0L),
                ackTime = d.getLong("ackTime") ?: 0L,
                status = GuardianAck.AckStatus.valueOf(d.getString("status") ?: GuardianAck.AckStatus.RECEIVED.name),
                message = d.getString("message")
            )
            db.guardianAckDao().insert(ga)
        }
    }
}
