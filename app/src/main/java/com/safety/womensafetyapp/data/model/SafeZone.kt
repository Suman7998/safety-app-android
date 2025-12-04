package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "safe_zones")
data class SafeZone(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: ZoneType,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val phone: String? = null,
    val isVerified: Boolean = true,
    val addedBy: String = "system" // "system" or user ID
) {
    enum class ZoneType {
        POLICE_STATION, HOSPITAL, SHELTER, OTHER
    }
}
