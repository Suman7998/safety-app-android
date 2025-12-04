package com.safety.womensafetyapp.ui.ml

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.safety.womensafetyapp.R
import com.safety.womensafetyapp.data.AppDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MLInsightsActivity : AppCompatActivity() {
    private lateinit var riskText: TextView
    private lateinit var safeText: TextView
    private lateinit var unsafeText: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_insights)

        db = AppDatabase.getDatabase(this)
        riskText = findViewById(R.id.riskText)
        safeText = findViewById(R.id.safeZonesText)
        unsafeText = findViewById(R.id.unsafeZonesText)

        CoroutineScope(Dispatchers.IO).launch {
            val risk = computeRisk()
            val safe = latestSafeZones()
            val unsafe = latestUnsafeZones()
            withContext(Dispatchers.Main) {
                riskText.text = risk
                safeText.text = safe
                unsafeText.text = unsafe
            }
        }
    }

    private fun computeRisk(): String {
        val now = System.currentTimeMillis()
        val since = now - 30L * 24 * 60 * 60 * 1000
        val cu1 = db.query(SimpleSQLiteQuery("SELECT COUNT(*) AS cnt FROM unsafe_zones WHERE reportDate >= ?", arrayOf(since)))
        val unsafeCount = cu1.use { if (it.moveToFirst()) it.getInt(it.getColumnIndexOrThrow("cnt")) else 0 }
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val isNight = (hour < 6 || hour >= 22)
        val cu2 = db.query(SimpleSQLiteQuery("SELECT COUNT(*) AS cnt FROM emergency_contacts"))
        val contacts = cu2.use { if (it.moveToFirst()) it.getInt(it.getColumnIndexOrThrow("cnt")) else 0 }

        var score = 0
        score += (unsafeCount.coerceAtMost(200) / 2)
        if (isNight) score += 15
        if (contacts == 0) score += 20 else if (contacts < 3) score += 10
        if (score > 100) score = 100

        val tips = mutableListOf<String>()
        if (contacts == 0) tips += "Add at least one emergency contact."
        if (isNight) tips += "Avoid isolated areas at night and prefer well-lit routes."
        if (unsafeCount > 20) tips += "Many unsafe reports in 30 days—be cautious."
        val tipsText = if (tips.isEmpty()) "Keep your phone charged and location on for quick help." else tips.joinToString("\n- ", prefix = "\n- ")
        return "Safety Risk Score: $score/100$tipsText"
    }

    private fun latestSafeZones(): String {
        val c = db.query(SimpleSQLiteQuery("SELECT name, type, address FROM safe_zones ORDER BY id DESC LIMIT 5"))
        val rows = mutableListOf<String>()
        c.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndexOrThrow("name"))
                val type = it.getString(it.getColumnIndexOrThrow("type"))
                val addr = it.getString(it.getColumnIndexOrThrow("address"))
                rows += "- $name [$type] - $addr"
            }
        }
        return if (rows.isEmpty()) "No safe zones stored yet." else rows.joinToString("\n")
    }

    private fun latestUnsafeZones(): String {
        val c = db.query(SimpleSQLiteQuery("SELECT latitude, longitude, dangerLevel, isVerified FROM unsafe_zones ORDER BY id DESC LIMIT 5"))
        val rows = mutableListOf<String>()
        c.use {
            while (it.moveToNext()) {
                val lat = it.getDouble(it.getColumnIndexOrThrow("latitude"))
                val lng = it.getDouble(it.getColumnIndexOrThrow("longitude"))
                val level = it.getString(it.getColumnIndexOrThrow("dangerLevel"))
                val verified = it.getInt(it.getColumnIndexOrThrow("isVerified")) == 1
                rows += "- ${"%.4f".format(lat)}, ${"%.4f".format(lng)} - $level${if (verified) " [VERIFIED]" else ""}"
            }
        }
        return if (rows.isEmpty()) "No unsafe zones stored yet." else rows.joinToString("\n")
    }
}
