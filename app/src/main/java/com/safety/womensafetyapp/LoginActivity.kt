package com.safety.womensafetyapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.safety.womensafetyapp.notifications.AlertsManager
import com.safety.womensafetyapp.data.auth.AuthRepository
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Locale

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton: MaterialButton
    private lateinit var forgotPasswordButton: MaterialButton
    private val authRepository = AuthRepository()

    private companion object {
        const val NOTIF_PERMISSION_REQUEST = 1010
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ensure default credentials exist
        ensureDefaultCredentials()

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)

        loginButton.setOnClickListener { attemptLogin() }
        registerButton.setOnClickListener { openRegisterDialog() }
        forgotPasswordButton.setOnClickListener { showForgotPasswordDialog() }
    }

    private fun ensureDefaultCredentials() {
        val prefs = getSharedPreferences("WomenSafetyAuth", MODE_PRIVATE)
        if (!prefs.contains("username") || !prefs.contains("password")) {
            prefs.edit()
                .putString("username", "Vanessa")
                .putString("password", "123456789")
                .apply()
        }
    }

    private fun attemptLogin() {
        val enteredUser = usernameInput.text?.toString()?.trim() ?: ""
        val enteredPass = passwordInput.text?.toString() ?: ""

        if (enteredUser.isEmpty()) {
            usernameInput.error = "Username required"
            usernameInput.requestFocus()
            return
        }
        if (enteredPass.isEmpty()) {
            passwordInput.error = "Password required"
            passwordInput.requestFocus()
            return
        }

        lifecycleScope.launch {
            try {
                authRepository.signInWithUsername(enteredUser, enteredPass)

                maybeRequestNotificationPermissionAndNotify()

                val title = "Mumbai Safety Alerts"
                val content = "Hospitals: 3 • Police: 3 • Shelters: 0 • Unsafe (30d): 0\n\n" +
                        "Nearby Safe Places:\n" +
                        "• Sir JJ Hospital (Green)\n" +
                        "• KEM Hospital (Green)\n" +
                        "• Lilavati Hospital (Green)\n" +
                        "• Mumbai Police HQ (Yellow)\n" +
                        "• Colaba Police Station (Yellow)\n" +
                        "• Dadar Police Station (Yellow)"

                MaterialAlertDialogBuilder(this@LoginActivity)
                    .setTitle(title)
                    .setMessage(content)
                    .setPositiveButton("Continue") { _, _ ->
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    .setCancelable(false)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun maybeRequestNotificationPermissionAndNotify() {
        val needsPermission = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED

        if (needsPermission) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIF_PERMISSION_REQUEST
            )
        } else {
            try { AlertsManager.showSummary(this) } catch (_: Exception) { }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIF_PERMISSION_REQUEST) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (granted) {
                try { AlertsManager.showSummary(this) } catch (_: Exception) { }
            }
        }
    }

    private fun openRegisterDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_register, null)
        val regUsername = view.findViewById<TextInputEditText>(R.id.regUsernameInput)
        val regPassword = view.findViewById<TextInputEditText>(R.id.regPasswordInput)
        val regConfirm = view.findViewById<TextInputEditText>(R.id.regConfirmPasswordInput)
        val regPhone = view.findViewById<TextInputEditText>(R.id.regPhoneInput)
        val regAge = view.findViewById<TextInputEditText>(R.id.regAgeInput)
        val regLocation = view.findViewById<TextInputEditText>(R.id.regLocationInput)

        MaterialAlertDialogBuilder(this)
            .setTitle("New Registration")
            .setView(view)
            .setPositiveButton("Register") { _, _ ->
                val u = regUsername.text?.toString()?.trim().orEmpty()
                val p = regPassword.text?.toString().orEmpty()
                val c = regConfirm.text?.toString().orEmpty()
                val ph = regPhone.text?.toString()?.trim().orEmpty()
                val age = regAge.text?.toString()?.trim().orEmpty()
                val loc = regLocation.text?.toString()?.trim().orEmpty()

                if (u.isEmpty()) { toast("Enter username"); return@setPositiveButton }
                if (p.length < 6) { toast("Password must be at least 6 chars"); return@setPositiveButton }
                if (p != c) { toast("Passwords do not match"); return@setPositiveButton }
                if (ph.length < 10) { toast("Enter valid phone number"); return@setPositiveButton }
                if (age.isEmpty()) { toast("Enter age"); return@setPositiveButton }
                if (loc.isEmpty()) { toast("Enter location"); return@setPositiveButton }
                val email = "${u.lowercase(Locale.ROOT)}@safetyapp.local"
                lifecycleScope.launch {
                    try {
                        authRepository.registerUser(email, p, u, ph, age, loc)
                        toast("Registration successful. You can login now.")
                        usernameInput.setText(u)
                        passwordInput.setText("")
                    } catch (e: Exception) {
                        toast(e.message ?: "Registration failed")
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showForgotPasswordDialog() {
        val prefs = getSharedPreferences("WomenSafetyAuth", MODE_PRIVATE)
        val savedUser = prefs.getString("username", "Vanessa")
        MaterialAlertDialogBuilder(this)
            .setTitle("Forgot Password")
            .setMessage("Username: ${savedUser}\nDefault password is 123456789 unless you changed it during registration.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
