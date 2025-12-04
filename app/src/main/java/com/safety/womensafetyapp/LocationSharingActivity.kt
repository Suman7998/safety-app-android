package com.safety.womensafetyapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import java.util.Locale

class LocationSharingActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var locationText: TextView
    private lateinit var coordinatesText: TextView
    private lateinit var shareLocationButton: MaterialButton
    private lateinit var shareEmergencyButton: MaterialButton
    private lateinit var liveTrackingSwitch: MaterialSwitch
    private lateinit var trackingDurationLayout: View
    private lateinit var duration15MinButton: MaterialButton
    private lateinit var duration1HourButton: MaterialButton
    private lateinit var durationCustomButton: MaterialButton
    private lateinit var autoShareSwitch: MaterialSwitch
    private lateinit var checkinRemindersSwitch: MaterialSwitch
    
    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private var locationListener: LocationListener? = null
    
    private companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_sharing)
        
        initializeViews()
        setupLocationServices()
        setupClickListeners()
        checkLocationPermission()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        locationText = findViewById(R.id.locationText)
        coordinatesText = findViewById(R.id.coordinatesText)
        shareLocationButton = findViewById(R.id.shareLocationButton)
        shareEmergencyButton = findViewById(R.id.shareEmergencyButton)
        liveTrackingSwitch = findViewById(R.id.liveTrackingSwitch)
        trackingDurationLayout = findViewById(R.id.trackingDurationLayout)
        duration15MinButton = findViewById(R.id.duration15MinButton)
        duration1HourButton = findViewById(R.id.duration1HourButton)
        durationCustomButton = findViewById(R.id.durationCustomButton)
        autoShareSwitch = findViewById(R.id.autoShareSwitch)
        checkinRemindersSwitch = findViewById(R.id.checkinRemindersSwitch)
        
        // Setup toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupLocationServices() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    private fun setupClickListeners() {
        shareLocationButton.setOnClickListener {
            shareCurrentLocation(false)
        }
        
        shareEmergencyButton.setOnClickListener {
            shareCurrentLocation(true)
        }
        
        liveTrackingSwitch.setOnCheckedChangeListener { _, isChecked ->
            trackingDurationLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
            if (isChecked) {
                startLiveTracking()
            } else {
                stopLiveTracking()
            }
        }
        
        duration15MinButton.setOnClickListener {
            setTrackingDuration(15)
        }
        
        duration1HourButton.setOnClickListener {
            setTrackingDuration(60)
        }
        
        durationCustomButton.setOnClickListener {
            showCustomDurationDialog()
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            != PackageManager.PERMISSION_GRANTED) {
            return
        }
        
        locationText.text = "Getting your location..."
        
        try {
            // Try GPS provider first
            val gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (gpsLocation != null) {
                currentLocation = gpsLocation
                updateLocationDisplay(gpsLocation)
                return
            }
            
            // Try network provider as fallback
            val networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (networkLocation != null) {
                currentLocation = networkLocation
                updateLocationDisplay(networkLocation)
                return
            }
            
            // If no last known location, request location updates
            requestLocationUpdates()
            
        } catch (e: SecurityException) {
            locationText.text = "Location permission error"
            Toast.makeText(this, "Location permission error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            != PackageManager.PERMISSION_GRANTED) {
            return
        }
        
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLocation = location
                updateLocationDisplay(location)
                locationManager.removeUpdates(this)
            }
            
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener!!
            )
            
            // Also try network provider
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener!!
            )
        } catch (e: Exception) {
            locationText.text = "Unable to get location updates"
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateLocationDisplay(location: Location) {
        val coordinates = "${String.format("%.6f", location.latitude)}, ${String.format("%.6f", location.longitude)}"
        coordinatesText.text = coordinates
        
        // Get address from coordinates
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val addressText = buildString {
                    if (address.thoroughfare != null) append("${address.thoroughfare}, ")
                    if (address.locality != null) append("${address.locality}, ")
                    if (address.adminArea != null) append("${address.adminArea}, ")
                    if (address.countryName != null) append(address.countryName)
                }
                locationText.text = addressText.trimEnd(',', ' ')
            } else {
                locationText.text = "Location: $coordinates"
            }
        } catch (e: Exception) {
            locationText.text = "Location: $coordinates"
        }
    }

    

    private fun shareCurrentLocation(isEmergency: Boolean) {
        currentLocation?.let { location ->
            val coordinates = "${location.latitude}, ${location.longitude}"
            val mapUrl = "https://maps.google.com/?q=$coordinates"
            
            val message = if (isEmergency) {
                "🚨 EMERGENCY ALERT 🚨\n\nI need help! My current location is:\n$mapUrl\n\nPlease contact me immediately or call emergency services.\n\nSent from Women Safety App"
            } else {
                "📍 My Current Location\n\n$mapUrl\n\nShared via Women Safety App"
            }
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                putExtra(Intent.EXTRA_SUBJECT, if (isEmergency) "Emergency Location Alert" else "Location Share")
            }
            
            startActivity(Intent.createChooser(intent, "Share Location"))
            
            Toast.makeText(
                this, 
                if (isEmergency) "Emergency location shared" else "Location shared", 
                Toast.LENGTH_SHORT
            ).show()
            
        } ?: run {
            Toast.makeText(this, "Location not available. Please try again.", Toast.LENGTH_SHORT).show()
            getCurrentLocation()
        }
    }

    private fun startLiveTracking() {
        Toast.makeText(this, "Live tracking started", Toast.LENGTH_SHORT).show()
        // In a real app, you would start a background service for live tracking
    }

    private fun stopLiveTracking() {
        Toast.makeText(this, "Live tracking stopped", Toast.LENGTH_SHORT).show()
        // In a real app, you would stop the background service
    }

    private fun setTrackingDuration(minutes: Int) {
        // Reset all button states
        duration15MinButton.isSelected = false
        duration1HourButton.isSelected = false
        durationCustomButton.isSelected = false
        
        // Set selected button
        when (minutes) {
            15 -> duration15MinButton.isSelected = true
            60 -> duration1HourButton.isSelected = true
        }
        
        Toast.makeText(this, "Tracking duration set to $minutes minutes", Toast.LENGTH_SHORT).show()
    }

    private fun showCustomDurationDialog() {
        // In a real app, you would show a time picker dialog
        durationCustomButton.isSelected = true
        duration15MinButton.isSelected = false
        duration1HourButton.isSelected = false
        
        Toast.makeText(this, "Custom duration selected", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                locationText.text = "Location permission denied"
                Toast.makeText(this, "Location permission is required for this feature", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh location when returning to the activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationListener?.let {
            locationManager.removeUpdates(it)
        }
    }
}
