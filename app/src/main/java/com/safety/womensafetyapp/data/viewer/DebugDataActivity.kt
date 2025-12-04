package com.safety.womensafetyapp.data.viewer

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.safety.womensafetyapp.R
import com.safety.womensafetyapp.data.AppDatabase
import com.safety.womensafetyapp.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random
import androidx.sqlite.db.SimpleSQLiteQuery

class DebugDataActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var seedButton: Button
    private lateinit var loadButton: Button
    private lateinit var pushButton: Button
    private lateinit var pullButton: Button
    private lateinit var recycler: RecyclerView
    private lateinit var progress: ProgressBar

    private lateinit var db: AppDatabase
    private val adapter = SimpleTextAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_data)

        db = AppDatabase.getDatabase(this)

        spinner = findViewById(R.id.tableSpinner)
        seedButton = findViewById(R.id.seedButton)
        loadButton = findViewById(R.id.loadButton)
        pushButton = findViewById(R.id.pushButton)
        pullButton = findViewById(R.id.pullButton)
        recycler = findViewById(R.id.recyclerView)
        progress = findViewById(R.id.progressBar)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val tables = listOf(
            "Safe Zones",
            "Unsafe Zones",
            "Emergency Contacts",
            "Message Logs",
            "Guardian Acks"
        )
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            tables
        )

        seedButton.setOnClickListener { seedSampleData() }
        loadButton.setOnClickListener { loadSelectedTable() }
        pushButton.setOnClickListener { pushToCloud() }
        pullButton.setOnClickListener { pullFromCloud() }
    }

    private fun setLoading(loading: Boolean) {
        progress.visibility = if (loading) View.VISIBLE else View.GONE
        seedButton.isEnabled = !loading
        loadButton.isEnabled = !loading
        pushButton.isEnabled = !loading
        pullButton.isEnabled = !loading
        spinner.isEnabled = !loading
    }

    private fun pushToCloud() {
        setLoading(true)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                com.safety.womensafetyapp.data.cloud.CloudSyncManager(applicationContext, db).pushAll()
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Pushed to cloud", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Push failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun pullFromCloud() {
        setLoading(true)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                com.safety.womensafetyapp.data.cloud.CloudSyncManager(applicationContext, db).pullAll()
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Pulled from cloud", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Pull failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun seedSampleData() {
        setLoading(true)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Create or upsert demo user
                val userId = "demo-user"
                val userDao = db.userDao()
                val existing = userDao.getUserById(userId)
                if (existing == null) {
                    userDao.insert(
                        User(
                            id = userId,
                            username = "demo",
                            email = "demo@example.com",
                            phone = "+10000000000"
                        )
                    )
                }

                // Seed SafeZones
                val safeTypes = SafeZone.ZoneType.values()
                val safeZoneDao = db.safeZoneDao()
                repeat(100) { i ->
                    val type = safeTypes[i % safeTypes.size]
                    val sz = SafeZone(
                        name = "Safe ${type.name} #$i",
                        type = type,
                        latitude = 28.6 + Random.nextDouble(-0.5, 0.5),
                        longitude = 77.2 + Random.nextDouble(-0.5, 0.5),
                        address = "Address $i",
                        phone = "+91${1000000000 + i}",
                        isVerified = (i % 3 == 0),
                        addedBy = "system"
                    )
                    safeZoneDao.insert(sz)
                }

                // Seed UnsafeZones
                val unsafeDao = db.unsafeZoneDao()
                val dangers = UnsafeZone.DangerLevel.values()
                repeat(100) { i ->
                    val uz = UnsafeZone(
                        latitude = 28.6 + Random.nextDouble(-0.5, 0.5),
                        longitude = 77.2 + Random.nextDouble(-0.5, 0.5),
                        address = "Reported Spot $i",
                        reportedBy = userId,
                        reportDate = System.currentTimeMillis() - Random.nextLong(0, 30L * 24 * 60 * 60 * 1000),
                        description = "Incident $i",
                        dangerLevel = dangers[i % dangers.size],
                        isVerified = (i % 5 == 0)
                    )
                    unsafeDao.insert(uz)
                }

                // Seed EmergencyContacts for demo user
                val contactDao = db.emergencyContactDao()
                val contactIds = mutableListOf<Long>()
                repeat(100) { i ->
                    val id = contactDao.insert(
                        EmergencyContact(
                            userId = userId,
                            name = "Guardian $i",
                            phone = "+9199${10000000 + i}",
                            email = "g$i@example.com",
                            relation = if (i % 2 == 0) "Friend" else "Family",
                            isPrimary = (i == 0)
                        )
                    )
                    contactIds += id
                }

                // Seed MessageLogs for demo user
                val msgDao = db.messageLogDao()
                val msgTypes = MessageLog.MessageType.values()
                val msgStatuses = MessageLog.MessageStatus.values()
                val msgIds = mutableListOf<Long>()
                repeat(100) { i ->
                    val id = msgDao.insert(
                        MessageLog(
                            userId = userId,
                            messageType = msgTypes[i % msgTypes.size],
                            timestamp = System.currentTimeMillis() - Random.nextLong(0, 7L * 24 * 60 * 60 * 1000),
                            content = "Message content $i",
                            recipients = listOf(
                                "+9199${10000000 + (i % 100)}",
                                "+9199${10000000 + ((i + 1) % 100)}"
                            ),
                            status = msgStatuses[i % msgStatuses.size],
                            latitude = 28.6 + Random.nextDouble(-0.5, 0.5),
                            longitude = 77.2 + Random.nextDouble(-0.5, 0.5),
                            locationName = "Loc $i"
                        )
                    )
                    msgIds += id
                }

                // Seed Guardian Acks linked to some messages and contacts
                val ackDao = db.guardianAckDao()
                val ackStatuses = GuardianAck.AckStatus.values()
                repeat(100) { i ->
                    val mid = msgIds[i % msgIds.size]
                    val gid = contactIds[i % contactIds.size]
                    ackDao.insert(
                        GuardianAck(
                            messageId = mid,
                            guardianId = gid,
                            ackTime = System.currentTimeMillis() - Random.nextLong(0, 7L * 24 * 60 * 60 * 1000),
                            status = ackStatuses[i % ackStatuses.size],
                            message = if (i % 3 == 0) "OK #$i" else null
                        )
                    )
                }

                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Seeded 100 rows per table", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Seeding failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadSelectedTable() {
        setLoading(true)
        val selection = spinner.selectedItemPosition
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val items: List<String> = when (selection) {
                    0 -> { // Safe Zones - list latest 100 directly
                        val c = db.query(SimpleSQLiteQuery("SELECT id, name, type, address FROM safe_zones ORDER BY id DESC LIMIT 100"))
                        val result = mutableListOf<String>()
                        while (c.moveToNext()) {
                            val id = c.getLong(c.getColumnIndexOrThrow("id"))
                            val name = c.getString(c.getColumnIndexOrThrow("name"))
                            val type = c.getString(c.getColumnIndexOrThrow("type"))
                            val address = c.getString(c.getColumnIndexOrThrow("address"))
                            result += "$id. $name [$type] - $address"
                        }
                        c.close()
                        result
                    }
                    1 -> { // Unsafe Zones - list latest 100 directly
                        val c = db.query(SimpleSQLiteQuery("SELECT id, latitude, longitude, dangerLevel, isVerified FROM unsafe_zones ORDER BY id DESC LIMIT 100"))
                        val result = mutableListOf<String>()
                        while (c.moveToNext()) {
                            val id = c.getLong(c.getColumnIndexOrThrow("id"))
                            val lat = c.getDouble(c.getColumnIndexOrThrow("latitude"))
                            val lng = c.getDouble(c.getColumnIndexOrThrow("longitude"))
                            val level = c.getString(c.getColumnIndexOrThrow("dangerLevel"))
                            val verified = c.getInt(c.getColumnIndexOrThrow("isVerified")) == 1
                            result += "$id. (${"%.4f".format(lat)}, ${"%.4f".format(lng)}) - $level${if (verified) " [VERIFIED]" else ""}"
                        }
                        c.close()
                        result
                    }
                    2 -> { // Emergency Contacts
                        // Since DAO returns Flow, query via message log pattern by adding repository or using observe is complex; fetch by raw query using user id via list once seeded
                        val userId = "demo-user"
                        val all = db.query(SimpleSQLiteQuery("SELECT * FROM emergency_contacts WHERE userId = ? ORDER BY isPrimary DESC, name ASC", arrayOf(userId)))
                        val result = mutableListOf<String>()
                        while (all.moveToNext()) {
                            val id = all.getLong(all.getColumnIndexOrThrow("id"))
                            val name = all.getString(all.getColumnIndexOrThrow("name"))
                            val phone = all.getString(all.getColumnIndexOrThrow("phone"))
                            val primary = all.getInt(all.getColumnIndexOrThrow("isPrimary")) == 1
                            result += "$id. $name - $phone${if (primary) " [PRIMARY]" else ""}"
                        }
                        all.close()
                        result
                    }
                    3 -> { // Message Logs
                        val userId = "demo-user"
                        val all = db.query(SimpleSQLiteQuery("SELECT * FROM message_logs WHERE userId = ? ORDER BY timestamp DESC LIMIT 100", arrayOf(userId)))
                        val result = mutableListOf<String>()
                        while (all.moveToNext()) {
                            val id = all.getLong(all.getColumnIndexOrThrow("id"))
                            val type = all.getString(all.getColumnIndexOrThrow("messageType"))
                            val status = all.getString(all.getColumnIndexOrThrow("status"))
                            result += "$id. $type - $status"
                        }
                        all.close()
                        result
                    }
                    else -> { // Guardian Acks
                        val all = db.query(SimpleSQLiteQuery("SELECT * FROM guardian_acks ORDER BY ackTime DESC LIMIT 100"))
                        val result = mutableListOf<String>()
                        while (all.moveToNext()) {
                            val id = all.getLong(all.getColumnIndexOrThrow("id"))
                            val status = all.getString(all.getColumnIndexOrThrow("status"))
                            val msgId = all.getLong(all.getColumnIndexOrThrow("messageId"))
                            val gid = all.getLong(all.getColumnIndexOrThrow("guardianId"))
                            result += "$id. msg#$msgId guardian#$gid - $status"
                        }
                        all.close()
                        result
                    }
                }
                withContext(Dispatchers.Main) {
                    adapter.submit(items)
                    setLoading(false)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setLoading(false)
                    Toast.makeText(this@DebugDataActivity, "Load failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

private class SimpleTextAdapter : RecyclerView.Adapter<SimpleVH>() {
    private val items = mutableListOf<String>()
    fun submit(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): SimpleVH {
        val v = android.view.LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return SimpleVH(v as android.widget.TextView)
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        holder.text.text = items[position]
    }
}

private class SimpleVH(val text: android.widget.TextView) : RecyclerView.ViewHolder(text)
