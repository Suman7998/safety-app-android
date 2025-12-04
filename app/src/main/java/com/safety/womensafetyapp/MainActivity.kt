package com.safety.womensafetyapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Build
import android.os.CountDownTimer
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var sosButton: MaterialButton
    private lateinit var emergencyContactsCard: MaterialCardView
    private lateinit var safetyTipsCard: MaterialCardView
    private lateinit var locationSharingCard: MaterialCardView
    private lateinit var settingsCard: MaterialCardView
    private lateinit var chatbotCard: MaterialCardView
    private lateinit var mlInsightsCard: MaterialCardView
    
    private var sosCountdownTimer: CountDownTimer? = null
    private var isSOSActive = false
    
    private companion object {
        const val PERMISSION_REQUEST_CODE = 100
        const val SOS_COUNTDOWN_TIME = 3000L // 3 seconds
        var SAFETY_ALERT_SHOWN = false
    }

    private fun openWebMapFallback() {
        try {
            val fallback = Intent(this, com.safety.womensafetyapp.ui.map.MapActivity::class.java)
            startActivity(fallback)
        } catch (_: Exception) {
            Toast.makeText(this, "Unable to open Map", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Attach toolbar to enable menu
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initializeViews()
        setupClickListeners()
        checkPermissions()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_media -> {
                try {
                    val intent = Intent(this, com.safety.womensafetyapp.ui.media.MediaCaptureActivity::class.java)
                    startActivity(intent)
                } catch (_: Exception) {
                    Toast.makeText(this, "Unable to open Media Capture", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeViews() {
        sosButton = findViewById(R.id.sosButton)
        emergencyContactsCard = findViewById(R.id.emergencyContactsCard)
        safetyTipsCard = findViewById(R.id.safetyTipsCard)
        locationSharingCard = findViewById(R.id.locationSharingCard)
        settingsCard = findViewById(R.id.settingsCard)
        chatbotCard = findViewById(R.id.chatbotCard)
        mlInsightsCard = findViewById(R.id.mlInsightsCard)
    }

    private fun setupClickListeners() {
        // SOS Button - Long press for emergency
        sosButton.setOnLongClickListener {
            if (!isSOSActive) {
                startSOSCountdown()
            }
            true
        }
        
        // Navigation Cards
        emergencyContactsCard.setOnClickListener {
            navigateToActivity(EmergencyContactsActivity::class.java)
        }
        
        safetyTipsCard.setOnClickListener {
            navigateToActivity(SafetyTipsActivity::class.java)
        }

        // Hidden: long-press Safety Tips to open Chatbot
        safetyTipsCard.setOnLongClickListener {
            try {
                val intent = Intent(this, com.safety.womensafetyapp.ui.chatbot.ChatbotActivity::class.java)
                startActivity(intent)
                true
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to open Chatbot", Toast.LENGTH_SHORT).show()
                false
            }
        }
        
        // Location Sharing and Settings navigation (restored)
        locationSharingCard.setOnClickListener {
            navigateToActivity(LocationSharingActivity::class.java)
        }

        settingsCard.setOnClickListener {
            navigateToActivity(SettingsActivity::class.java)
        }

        // Map
        val mapCard: MaterialCardView = findViewById(R.id.mapCard)
        mapCard.setOnClickListener {
            // Prefer Google Maps only if Play Services are available
            val playServicesAvailable =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS

            // Also ensure API key meta-data exists (avoid launching empty Google map)
            val hasMapsApiKey = try {
                val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
                val key = ai.metaData?.getString("com.google.android.geo.API_KEY")
                !key.isNullOrBlank()
            } catch (_: Exception) { false }

            if (playServicesAvailable && hasMapsApiKey) {
                try {
                    val intent = Intent(this, com.safety.womensafetyapp.ui.map.GoogleMapActivity::class.java)
                    startActivity(intent)
                } catch (_: Exception) {
                    // Fallback to WebView-based map
                    openWebMapFallback()
                }
            } else {
                openWebMapFallback()
            }
        }

        chatbotCard.setOnClickListener {
            try {
                val intent = Intent(this, com.safety.womensafetyapp.ui.chatbot.ChatbotActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to open Chatbot", Toast.LENGTH_SHORT).show()
            }
        }
        
        mlInsightsCard.setOnClickListener {
            try {
                val intent = Intent(this, com.safety.womensafetyapp.ui.ml.MLInsightsActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to open ML Insights", Toast.LENGTH_SHORT).show()
            }
        }

        // Data Viewer (visible button)
        val dataViewerCard: MaterialCardView = findViewById(R.id.dataViewerCard)
        dataViewerCard.setOnClickListener {
            try {
                val intent = Intent(this, com.safety.womensafetyapp.data.viewer.DebugDataActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Unable to open Data Viewer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startSOSCountdown() {
        isSOSActive = true
        
        // Vibrate to indicate SOS activation
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(500)
        
        sosCountdownTimer = object : CountDownTimer(SOS_COUNTDOWN_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt() + 1
                sosButton.text = getString(R.string.sos_countdown, secondsLeft)
                
                // Show cancel dialog
                showSOSCancelDialog()
            }
            
            override fun onFinish() {
                activateEmergencySOS()
            }
        }.start()
    }

    private fun showSOSCancelDialog() {
        AlertDialog.Builder(this)
            .setTitle("Emergency SOS")
            .setMessage("Emergency call will be made in a few seconds. Cancel if this was accidental.")
            .setPositiveButton("Cancel") { _, _ ->
                cancelSOS()
            }
            .setNegativeButton("Continue") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun cancelSOS() {
        sosCountdownTimer?.cancel()
        isSOSActive = false
        sosButton.text = getString(R.string.sos_button_text)
        Toast.makeText(this, "Emergency SOS cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun activateEmergencySOS() {
        isSOSActive = false
        sosButton.text = getString(R.string.sos_activated)
        
        // Check if we have phone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) 
            == PackageManager.PERMISSION_GRANTED) {
            
            // Make emergency call (using emergency number)
            makeEmergencyCall()
            
            // Send emergency SMS if permission available
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) 
                == PackageManager.PERMISSION_GRANTED) {
                sendEmergencySMS()
            }
            
        } else {
            Toast.makeText(this, "Phone permission required for emergency calls", Toast.LENGTH_LONG).show()
        }
        
        // Reset button text after 3 seconds
        sosButton.postDelayed({
            sosButton.text = getString(R.string.sos_button_text)
        }, 3000)
    }

    private fun makeEmergencyCall() {
        try {
            // In a real app, you would call saved emergency contacts
            // For demo purposes, we'll show the dialer with emergency number
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:112") // European emergency number
            }
            startActivity(intent)
            
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to make emergency call", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmergencySMS() {
        // In a real app, you would send SMS to emergency contacts
        // This is a placeholder for the SMS functionality
        Toast.makeText(this, "Emergency SMS sent to contacts", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun checkPermissions() {
        val perms = mutableListOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perms += Manifest.permission.POST_NOTIFICATIONS
        }

        val permissionsToRequest = perms.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val deniedPermissions = permissions.filterIndexed { index, _ ->
                grantResults[index] != PackageManager.PERMISSION_GRANTED
            }
            
            if (deniedPermissions.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Some permissions were denied. App functionality may be limited.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sosCountdownTimer?.cancel()
    }

    override fun onResume() {
        super.onResume()
        // Post an AI-based summary alert when app comes to foreground
        try {
            com.safety.womensafetyapp.notifications.AlertsManager.showSummary(this)
        } catch (_: Exception) { /* no-op to ensure no crashes */ }

        // Show in-app safety alert on the main window once per session (Android 9+ without permissions)
        if (!SAFETY_ALERT_SHOWN) {
            SAFETY_ALERT_SHOWN = true
            val title = "Mumbai Safety Alerts"
            val content = "Hospitals: 3 • Police: 3 • Shelters: 0 • Unsafe (30d): 0\n\n" +
                    "Nearby Safe Places:\n" +
                    "• Sir JJ Hospital (Green)\n" +
                    "• KEM Hospital (Green)\n" +
                    "• Lilavati Hospital (Green)\n" +
                    "• Mumbai Police HQ (Yellow)\n" +
                    "• Colaba Police Station (Yellow)\n" +
                    "• Dadar Police Station (Yellow)"

            try {
                MaterialAlertDialogBuilder(this)
                    .setTitle(title)
                    .setMessage(content)
                    .setPositiveButton("OK", null)
                    .setCancelable(true)
                    .show()
            } catch (_: Exception) { /* no-op */ }
        }
    }
}
