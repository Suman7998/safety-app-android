package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "unsafe_zones")
data class UnsafeZone(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val reportedBy: String, // User ID
    val reportDate: Long = System.currentTimeMillis(),
    val description: String? = null,
    val dangerLevel: DangerLevel = DangerLevel.MEDIUM,
    val isVerified: Boolean = false
) {
    enum class DangerLevel {
        LOW, MEDIUM, HIGH
    }
}
