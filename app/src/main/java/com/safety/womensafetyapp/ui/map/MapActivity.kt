package com.safety.womensafetyapp.ui.map

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.safety.womensafetyapp.R

class MapActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        webView = findViewById(R.id.mapWebView)
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        try {
            // Static Mumbai markers (no database)
            val hospitals = listOf(
                Triple(18.9647, 72.8331, "Sir JJ Hospital"),
                Triple(19.0020, 72.8430, "KEM Hospital"),
                Triple(19.0645, 72.8365, "Lilavati Hospital")
            )
            val police = listOf(
                Triple(18.9440, 72.8347, "Mumbai Police HQ"),
                Triple(18.9067, 72.8147, "Colaba Police Station"),
                Triple(19.0170, 72.8567, "Dadar Police Station")
            )

            val html = if (hasNetwork()) buildHtml(hospitals, police) else buildOfflineHtml()
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
        } catch (e: Exception) {
            // Graceful fallback page
            webView.loadData(
                "<html><body style='font-family:sans-serif;padding:16px'>" +
                        "<h3>Map unavailable</h3>" +
                        "<p>We couldn't display the map right now. Check your internet connection.</p>" +
                        "</body></html>",
                "text/html",
                "utf-8"
            )
        }
    }

    private fun buildHtml(hospitals: List<Triple<Double, Double, String>>, police: List<Triple<Double, Double, String>>): String {
        // Default center: Maharashtra, India (if no data)
        val defaultLat = 19.7515
        val defaultLng = 75.7139
        val centerLat = hospitals.firstOrNull()?.first ?: police.firstOrNull()?.first ?: defaultLat
        val centerLng = hospitals.firstOrNull()?.second ?: police.firstOrNull()?.second ?: defaultLng

        // Mumbai bounding box filter
        fun inMumbai(lat: Double, lng: Double): Boolean =
            lat in 18.8900..19.3000 && lng in 72.7700..73.1000

        // Filter DB markers to Mumbai
        val mumbaiHospitals = hospitals.filter { (lat, lng, _) -> inMumbai(lat, lng) }.toMutableList()
        val mumbaiPolice = police.filter { (lat, lng, _) -> inMumbai(lat, lng) }.toMutableList()

        // If none found, add sensible defaults in Mumbai
        if (mumbaiHospitals.isEmpty() && mumbaiPolice.isEmpty()) {
            mumbaiHospitals += listOf(
                Triple(18.9647, 72.8331, "Sir JJ Hospital"),
                Triple(19.0020, 72.8430, "KEM Hospital"),
                Triple(19.0645, 72.8365, "Lilavati Hospital")
            )
            mumbaiPolice += listOf(
                Triple(18.9440, 72.8347, "Mumbai Police HQ"),
                Triple(18.9067, 72.8147, "Colaba Police Station"),
                Triple(19.0170, 72.8567, "Dadar Police Station")
            )
        }

        val hospitalMarkers = mumbaiHospitals.joinToString("\n") { (lat, lng, name) ->
            "L.circleMarker([${lat}, ${lng}], {color: 'green', radius: 8}).addTo(map).bindPopup('${name.replace("'", "\\'")} (Hospital)');"
        }
        val policeMarkers = mumbaiPolice.joinToString("\n") { (lat, lng, name) ->
            "L.circleMarker([${lat}, ${lng}], {color: 'yellow', radius: 8}).addTo(map).bindPopup('${name.replace("'", "\\'")} (Police)');"
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
              <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
              <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />
              <script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>
              <style> html, body, #map { height: 100%; margin: 0; } </style>
            </head>
            <body>
              <div id=\"map\"></div>
              <script>
                // Define Mumbai bounds only
                var mumbaiBounds = L.latLngBounds(L.latLng(18.8900, 72.7700), L.latLng(19.3000, 73.1000));

                // Initialize map with bounds restrictions (strictly Mumbai)
                var map = L.map('map', {
                  maxBounds: mumbaiBounds,
                  maxBoundsViscosity: 1.0,
                  zoomControl: true,
                  worldCopyJump: false,
                  minZoom: 10
                });
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                  maxZoom: 19,
                  attribution: '© OpenStreetMap'
                }).addTo(map);
                // Fit to Mumbai by default
                map.fitBounds(mumbaiBounds);
                $hospitalMarkers
                $policeMarkers
              </script>
            </body>
            </html>
        """.trimIndent()
    }

    private fun buildOfflineHtml(): String {
        return """
            <!DOCTYPE html>
            <html>
            <head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
            <style>body{font-family:sans-serif;padding:16px}</style></head>
            <body>
            <h3>Map unavailable offline</h3>
            <p>Please connect to the internet to load the map tiles. You can still add Safe Zones via the Data Viewer.</p>
            </body></html>
        """.trimIndent()
    }

    private fun hasNetwork(): Boolean {
        return try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = cm.activeNetwork ?: return false
            val caps = cm.getNetworkCapabilities(network) ?: return false
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } catch (_: Exception) { false }
    }
}
