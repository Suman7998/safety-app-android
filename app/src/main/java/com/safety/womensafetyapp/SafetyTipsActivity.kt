package com.safety.womensafetyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SafetyTipsActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var personalSafetyCard: MaterialCardView
    private lateinit var onlineSafetyCard: MaterialCardView
    private lateinit var travelSafetyCard: MaterialCardView
    private lateinit var workplaceSafetyCard: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safety_tips)
        
        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        personalSafetyCard = findViewById(R.id.personalSafetyCard)
        onlineSafetyCard = findViewById(R.id.onlineSafetyCard)
        travelSafetyCard = findViewById(R.id.travelSafetyCard)
        workplaceSafetyCard = findViewById(R.id.workplaceSafetyCard)
        
        // Setup toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupClickListeners() {
        personalSafetyCard.setOnClickListener {
            showSafetyTipsDialog("Personal Safety", getPersonalSafetyTips())
        }
        
        onlineSafetyCard.setOnClickListener {
            showSafetyTipsDialog("Online Safety", getOnlineSafetyTips())
        }
        
        travelSafetyCard.setOnClickListener {
            showSafetyTipsDialog("Travel Safety", getTravelSafetyTips())
        }
        
        workplaceSafetyCard.setOnClickListener {
            showSafetyTipsDialog("Workplace Safety", getWorkplaceSafetyTips())
        }
    }

    private fun showSafetyTipsDialog(title: String, tips: List<String>) {
        val tipsText = tips.joinToString("\n\n") { "• $it" }
        
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(tipsText)
            .setPositiveButton("Got it", null)
            .show()
    }

    private fun getPersonalSafetyTips(): List<String> {
        return listOf(
            "Always trust your instincts. If something feels wrong, remove yourself from the situation.",
            "Stay aware of your surroundings. Avoid distractions like headphones in unfamiliar areas.",
            "Keep your phone charged and share your location with trusted contacts.",
            "Walk confidently and make eye contact. Appear alert and purposeful.",
            "Avoid isolated areas, especially at night. Stay in well-lit, populated areas.",
            "Learn basic self-defense techniques and carry legal safety tools.",
            "Don't share personal information with strangers.",
            "Have an emergency plan and know escape routes from places you frequent.",
            "Keep emergency contacts easily accessible on your phone.",
            "If followed, go to a public place like a store or police station."
        )
    }

    private fun getOnlineSafetyTips(): List<String> {
        return listOf(
            "Use strong, unique passwords for all accounts and enable two-factor authentication.",
            "Be cautious about sharing personal information on social media.",
            "Don't accept friend requests from people you don't know personally.",
            "Be wary of online dating. Meet in public places and tell someone your plans.",
            "Never share intimate photos or videos - they can be used against you.",
            "Report and block users who harass or threaten you online.",
            "Be skeptical of too-good-to-be-true offers or requests for money.",
            "Keep your software and apps updated to protect against security vulnerabilities.",
            "Use privacy settings to control who can see your posts and information.",
            "If you're being cyberbullied, document everything and report it to authorities."
        )
    }

    private fun getTravelSafetyTips(): List<String> {
        return listOf(
            "Research your destination beforehand and know safe areas vs. areas to avoid.",
            "Share your travel itinerary with trusted contacts.",
            "Keep copies of important documents in separate locations.",
            "Use reputable transportation services and avoid unlicensed taxis.",
            "Stay in well-reviewed accommodations in safe neighborhoods.",
            "Don't display expensive jewelry or electronics openly.",
            "Keep some cash hidden separately from your main wallet.",
            "Trust your instincts about people and situations.",
            "Learn basic phrases in the local language, including how to ask for help.",
            "Have emergency contacts for your destination, including local police and embassy."
        )
    }

    private fun getWorkplaceSafetyTips(): List<String> {
        return listOf(
            "Know your company's harassment policy and reporting procedures.",
            "Document any incidents of harassment or inappropriate behavior.",
            "Set clear professional boundaries and communicate them assertively.",
            "Avoid being alone with someone who makes you uncomfortable.",
            "Trust your instincts about workplace situations and people.",
            "Build relationships with trusted colleagues who can support you.",
            "Keep personal information private from coworkers when appropriate.",
            "Report incidents to HR or management, and follow up in writing.",
            "Know your legal rights regarding workplace harassment and discrimination.",
            "Consider seeking support from employee assistance programs or external counselors."
        )
    }
}
