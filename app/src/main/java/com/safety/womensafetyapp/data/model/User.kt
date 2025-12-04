package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String, // Can be auth ID or generated UUID
    val username: String,
    val email: String? = null,
    val phone: String,
    val fullName: String? = null,
    val profileImage: String? = null,
    val emergencyMessage: String = "I need help! Please check on me.",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val lastActive: Long = System.currentTimeMillis(),
    val homeLatitude: Double? = null,
    val homeLongitude: Double? = null,
    val workLatitude: Double? = null,
    val workLongitude: Double? = null,
    val isLocationSharingEnabled: Boolean = true,
    val sosContacts: List<Long> = emptyList() // List of emergency contact IDs
)
