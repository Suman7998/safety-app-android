package com.safety.womensafetyapp.ui.chatbot

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.safety.womensafetyapp.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.safety.womensafetyapp.data.AppDatabase
import androidx.sqlite.db.SimpleSQLiteQuery

class ChatbotActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var input: EditText
    private lateinit var send: ImageButton
    private val adapter = ChatAdapter()
    private lateinit var bot: SimpleSafetyBot
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        db = AppDatabase.getDatabase(this)
        bot = SimpleSafetyBot()

        recycler = findViewById(R.id.chatRecycler)
        input = findViewById(R.id.messageInput)
        send = findViewById(R.id.sendButton)

        recycler.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recycler.adapter = adapter

        // Greeting
        adapter.addBot("Hi! I'm your Safety Assistant. Ask me about SOS, safety tips, emergency contacts, safe/unsafe zones, location sharing, or type 'risk' for your safety score.")

        send.setOnClickListener { submit() }
        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                submit(); true
            } else false
        }
    }

    private fun submit() {
        val text = input.text.toString().trim()
        if (text.isEmpty()) return
        adapter.addUser(text)
        input.setText("")
        lifecycleScope.launch(Dispatchers.IO) {
            val reply = bot.reply(text, db)
            withContext(Dispatchers.Main) {
                adapter.addBot(reply)
                recycler.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}

private data class ChatMessage(val fromBot: Boolean, val text: String)

private class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ChatMessage>()

    fun addUser(text: String) { items += ChatMessage(false, text); notifyItemInserted(items.lastIndex) }
    fun addBot(text: String) { items += ChatMessage(true, text); notifyItemInserted(items.lastIndex) }

    override fun getItemViewType(position: Int): Int = if (items[position].fromBot) 1 else 0

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = android.view.LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val v = inflater.inflate(R.layout.item_chat_bot, parent, false)
            BotVH(v as android.widget.TextView)
        } else {
            val v = inflater.inflate(R.layout.item_chat_user, parent, false)
            UserVH(v as android.widget.TextView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = items[position]
        when (holder) {
            is BotVH -> holder.text.text = msg.text
            is UserVH -> holder.text.text = msg.text
        }
    }

    override fun getItemCount(): Int = items.size

    private class BotVH(val text: android.widget.TextView) : RecyclerView.ViewHolder(text)
    private class UserVH(val text: android.widget.TextView) : RecyclerView.ViewHolder(text)
}

private class SimpleSafetyBot {
    private val tips = listOf(
        "Stay in well-lit areas and avoid isolated places.",
        "Share your live location with a trusted contact.",
        "Keep emergency numbers on speed dial.",
        "Trust your instincts—leave if you feel unsafe.",
        "Use the SOS in the app: long-press the SOS button to trigger help."
    )

    suspend fun reply(q: String, db: AppDatabase): String {
        val s = q.lowercase()
        // Intent classification (lightweight keyword scoring)
        val intent = when {
            listOf("hello","hi","hey").any { s.contains(it) } -> "greet"
            listOf("sos","emergency","panic").any { s.contains(it) } -> "sos"
            listOf("tip","advice","safe advice").any { s.contains(it) } -> "tips"
            listOf("contact","guardian").any { s.contains(it) } -> "contacts"
            listOf("safe zone","police","hospital","shelter").any { s.contains(it) } -> "safezones"
            listOf("unsafe","danger","risk near").any { s.contains(it) } -> "unsafezones"
            listOf("share location","location sharing","live location").any { s.contains(it) } -> "location"
            listOf("risk","am i safe","safety score").any { s.contains(it) } -> "risk"
            listOf("help","what can you do","options").any { s.contains(it) } -> "help"
            else -> "unknown"
        }

        return when (intent) {
            "greet" -> "Hello! How can I help you stay safe today?"
            "sos" -> "Long-press the red SOS button on the home screen. It starts a 3s countdown, places a call (112) and can send SMS to contacts if permitted."
            "tips" -> "Safety Tips:\n- " + tips.joinToString("\n- ")
            "contacts" -> "Manage emergency contacts from the home screen → 'Emergency Contacts'. Set a Primary contact for quick access."
            "safezones" -> latestSafeZones(db)
            "unsafezones" -> latestUnsafeZones(db)
            "location" -> "Enable location permissions. Use 'Location Sharing' from the home screen to share your live location with guardians."
            "risk" -> safetyRisk(db)
            "help" -> "You can ask me about: SOS, safety tips, emergency contacts, safe/unsafe zones, location sharing, or type 'risk' for your safety score."
            else -> "I didn't fully get that. Try asking about SOS, tips, contacts, safe/unsafe zones, location, or 'risk'."
        }
    }

    private fun latestSafeZones(db: AppDatabase): String {
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
        return if (rows.isEmpty()) "No safe zones stored yet. Seed data in Data Viewer (long-press Settings)." else "Latest Safe Zones:\n" + rows.joinToString("\n")
    }

    private fun latestUnsafeZones(db: AppDatabase): String {
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
        return if (rows.isEmpty()) "No unsafe zones stored yet. Seed data in Data Viewer (long-press Settings)." else "Recent Unsafe Zones:\n" + rows.joinToString("\n")
    }

    private fun safetyRisk(db: AppDatabase): String {
        // Factors: recent unsafe zones count (30d), night time penalty, contact availability
        val now = System.currentTimeMillis()
        val since = now - 30L * 24 * 60 * 60 * 1000

        val cu1 = db.query(SimpleSQLiteQuery("SELECT COUNT(*) AS cnt FROM unsafe_zones WHERE reportDate >= ?", arrayOf(since)))
        val unsafeCount = cu1.use { if (it.moveToFirst()) it.getInt(it.getColumnIndexOrThrow("cnt")) else 0 }

        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val isNight = (hour < 6 || hour >= 22)

        val cu2 = db.query(SimpleSQLiteQuery("SELECT COUNT(*) AS cnt FROM emergency_contacts"))
        val contacts = cu2.use { if (it.moveToFirst()) it.getInt(it.getColumnIndexOrThrow("cnt")) else 0 }

        // Score 0 (safe) to 100 (risky)
        var score = 0
        score += (unsafeCount.coerceAtMost(200) / 2) // up to 100 via unsafe density
        if (isNight) score += 15
        if (contacts == 0) score += 20 else if (contacts < 3) score += 10
        if (score > 100) score = 100

        val tips = mutableListOf<String>()
        if (contacts == 0) tips += "Add at least one emergency contact."
        if (isNight) tips += "Avoid isolated areas at night and prefer well-lit routes."
        if (unsafeCount > 20) tips += "Be cautious—many unsafe reports in the last 30 days."

        val tipsText = if (tips.isEmpty()) "Keep your phone charged and location on for quick help." else tips.joinToString("\n- ", prefix = "\n- ")
        return "Your Safety Risk Score: $score/100.$tipsText"
    }
}
