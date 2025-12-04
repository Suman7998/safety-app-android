package com.safety.womensafetyapp.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.safety.womensafetyapp.R
import android.widget.Toast

class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        // Be defensive when retrieving the fragment
        val fragment = supportFragmentManager.findFragmentById(R.id.mapFragment)
        val mapFragment = fragment as? SupportMapFragment
        if (mapFragment != null) {
            try {
                mapFragment.getMapAsync(this)
            } catch (_: Exception) {
                Toast.makeText(this, "Unable to initialize Google Map", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Map fragment not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        try {
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isCompassEnabled = true
        } catch (_: Exception) { /* UI settings best-effort */ }

        // Restrict camera strictly to Mumbai, India
        try {
            // Approx Mumbai bounding box
            val mumbaiBounds = LatLngBounds(
                LatLng(18.8900, 72.7700),  // SW
                LatLng(19.3000, 73.1000)   // NE
            )
            googleMap?.setLatLngBoundsForCameraTarget(mumbaiBounds)
            googleMap?.setMinZoomPreference(10.0f)
            googleMap?.setMaxZoomPreference(20.0f)
            // Immediately set camera inside Mumbai to avoid world view
            val mumbaiCenter = LatLng(19.0760, 72.8777)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mumbaiCenter, 12f))
        } catch (_: Exception) { /* ignore if bounds fail */ }

        renderMarkersSafely()
    }

    private fun renderMarkersSafely() {
        try {
            val gmap = googleMap ?: return
            // Static Mumbai markers (no database)
            val mumbaiHospitals = listOf(
                Triple(18.9647, 72.8331, "Sir JJ Hospital"),
                Triple(19.0020, 72.8430, "KEM Hospital"),
                Triple(19.0645, 72.8365, "Lilavati Hospital")
            )
            val mumbaiPolice = listOf(
                Triple(18.9440, 72.8347, "Mumbai Police HQ"),
                Triple(18.9067, 72.8147, "Colaba Police Station"),
                Triple(19.0170, 72.8567, "Dadar Police Station")
            )

            var firstLatLng: LatLng? = null
            mumbaiHospitals.forEach { (lat, lng, name) ->
                val p = LatLng(lat, lng)
                if (firstLatLng == null) firstLatLng = p
                gmap.addMarker(
                    MarkerOptions()
                        .position(p)
                        .title(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
            }
            mumbaiPolice.forEach { (lat, lng, name) ->
                val p = LatLng(lat, lng)
                if (firstLatLng == null) firstLatLng = p
                gmap.addMarker(
                    MarkerOptions()
                        .position(p)
                        .title(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                )
            }

            // Default center: Mumbai, India if no markers
            val defaultMumbai = LatLng(19.0760, 72.8777)
            val target = firstLatLng ?: defaultMumbai
            val zoom = if (firstLatLng == null) 12f else 13f
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, zoom))
        } catch (_: Exception) {
            // No crash — leave map empty if something goes wrong
            Toast.makeText(this, "Map loaded without markers", Toast.LENGTH_SHORT).show()
        }
    }
}
