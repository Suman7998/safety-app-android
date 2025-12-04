package com.safety.womensafetyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var profileInitial: TextView
    private lateinit var changePhotoButton: MaterialButton
    private lateinit var nameInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var emergencyMessageInput: TextInputEditText
    private lateinit var emergencyNotificationsSwitch: MaterialSwitch
    private lateinit var safetyTipsNotificationsSwitch: MaterialSwitch
    private lateinit var locationNotificationsSwitch: MaterialSwitch
    private lateinit var dataCollectionSwitch: MaterialSwitch
    private lateinit var crashReportsSwitch: MaterialSwitch
    private lateinit var privacyPolicyButton: MaterialButton
    private lateinit var termsOfServiceButton: MaterialButton
    private lateinit var contactSupportButton: MaterialButton
    private lateinit var saveSettingsButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        initializeViews()
        setupClickListeners()
        loadSettings()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        profileInitial = findViewById(R.id.profileInitial)
        changePhotoButton = findViewById(R.id.changePhotoButton)
        nameInput = findViewById(R.id.nameInput)
        phoneInput = findViewById(R.id.phoneInput)
        emergencyMessageInput = findViewById(R.id.emergencyMessageInput)
        emergencyNotificationsSwitch = findViewById(R.id.emergencyNotificationsSwitch)
        safetyTipsNotificationsSwitch = findViewById(R.id.safetyTipsNotificationsSwitch)
        locationNotificationsSwitch = findViewById(R.id.locationNotificationsSwitch)
        dataCollectionSwitch = findViewById(R.id.dataCollectionSwitch)
        crashReportsSwitch = findViewById(R.id.crashReportsSwitch)
        privacyPolicyButton = findViewById(R.id.privacyPolicyButton)
        termsOfServiceButton = findViewById(R.id.termsOfServiceButton)
        contactSupportButton = findViewById(R.id.contactSupportButton)
        saveSettingsButton = findViewById(R.id.saveSettingsButton)
        
        // Setup toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupClickListeners() {
        changePhotoButton.setOnClickListener {
            // In a real app, you would open image picker
            Toast.makeText(this, "Photo selection feature coming soon", Toast.LENGTH_SHORT).show()
        }
        
        saveSettingsButton.setOnClickListener {
            saveSettings()
        }
        
        privacyPolicyButton.setOnClickListener {
            openWebPage("https://example.com/privacy-policy")
        }
        
        termsOfServiceButton.setOnClickListener {
            openWebPage("https://example.com/terms-of-service")
        }
        
        contactSupportButton.setOnClickListener {
            openEmailSupport()
        }
        
        // Update profile initial when name changes
        nameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                updateProfileInitial()
            }
        }
    }

    private fun loadSettings() {
        // In a real app, you would load from SharedPreferences or database
        // For demo purposes, we'll set some default values
        nameInput.setText("User")
        phoneInput.setText("")
        emergencyMessageInput.setText("I need help! Please contact me immediately or call emergency services.")
        
        // Set default switch states
        emergencyNotificationsSwitch.isChecked = true
        safetyTipsNotificationsSwitch.isChecked = false
        locationNotificationsSwitch.isChecked = true
        dataCollectionSwitch.isChecked = false
        crashReportsSwitch.isChecked = true
        
        updateProfileInitial()
    }

    private fun saveSettings() {
        val name = nameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val emergencyMessage = emergencyMessageInput.text.toString().trim()
        
        // Validate input
        if (name.isEmpty()) {
            nameInput.error = "Name is required"
            nameInput.requestFocus()
            return
        }
        
        if (phone.isNotEmpty() && phone.length < 10) {
            phoneInput.error = "Please enter a valid phone number"
            phoneInput.requestFocus()
            return
        }
        
        if (emergencyMessage.isEmpty()) {
            emergencyMessageInput.error = "Emergency message is required"
            emergencyMessageInput.requestFocus()
            return
        }
        
        // In a real app, you would save to SharedPreferences or database
        saveToPreferences(name, phone, emergencyMessage)
        
        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
        updateProfileInitial()
    }

    private fun saveToPreferences(name: String, phone: String, emergencyMessage: String) {
        val sharedPref = getSharedPreferences("WomenSafetySettings", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_name", name)
            putString("user_phone", phone)
            putString("emergency_message", emergencyMessage)
            putBoolean("emergency_notifications", emergencyNotificationsSwitch.isChecked)
            putBoolean("safety_tips_notifications", safetyTipsNotificationsSwitch.isChecked)
            putBoolean("location_notifications", locationNotificationsSwitch.isChecked)
            putBoolean("data_collection", dataCollectionSwitch.isChecked)
            putBoolean("crash_reports", crashReportsSwitch.isChecked)
            apply()
        }
    }

    private fun updateProfileInitial() {
        val name = nameInput.text.toString().trim()
        val initial = if (name.isNotEmpty()) {
            name.first().uppercaseChar().toString()
        } else {
            "U"
        }
        profileInitial.text = initial
    }

    private fun openWebPage(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open web page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openEmailSupport() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("support@womensafetyapp.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Women Safety App - Support Request")
                putExtra(Intent.EXTRA_TEXT, "Please describe your issue or feedback:\n\n")
            }
            
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open email", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload settings when returning to the activity
        loadSettingsFromPreferences()
    }

    private fun loadSettingsFromPreferences() {
        val sharedPref = getSharedPreferences("WomenSafetySettings", MODE_PRIVATE)
        
        val savedName = sharedPref.getString("user_name", "User") ?: "User"
        val savedPhone = sharedPref.getString("user_phone", "") ?: ""
        val savedMessage = sharedPref.getString("emergency_message", 
            "I need help! Please contact me immediately or call emergency services.") ?: ""
        
        nameInput.setText(savedName)
        phoneInput.setText(savedPhone)
        emergencyMessageInput.setText(savedMessage)
        
        emergencyNotificationsSwitch.isChecked = sharedPref.getBoolean("emergency_notifications", true)
        safetyTipsNotificationsSwitch.isChecked = sharedPref.getBoolean("safety_tips_notifications", false)
        locationNotificationsSwitch.isChecked = sharedPref.getBoolean("location_notifications", true)
        dataCollectionSwitch.isChecked = sharedPref.getBoolean("data_collection", false)
        crashReportsSwitch.isChecked = sharedPref.getBoolean("crash_reports", true)
        
        updateProfileInitial()
    }
}
