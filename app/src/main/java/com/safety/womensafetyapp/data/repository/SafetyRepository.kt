package com.safety.womensafetyapp.data.repository

import android.content.Context
import com.safety.womensafetyapp.data.AppDatabase
import com.safety.womensafetyapp.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*

class SafetyRepository private constructor(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    
    // User operations
    suspend fun insertUser(user: User) = database.userDao().insert(user)
    suspend fun updateUser(user: User) = database.userDao().update(user)
    suspend fun getUserById(userId: String) = database.userDao().getUserById(userId)
    fun observeUser(userId: String) = database.userDao().observeUserById(userId)
    
    // Emergency Contacts
    suspend fun addEmergencyContact(contact: EmergencyContact) = 
        database.emergencyContactDao().insert(contact)
    
    suspend fun updateEmergencyContact(contact: EmergencyContact) = 
        database.emergencyContactDao().update(contact)
    
    fun getEmergencyContacts(userId: String) = 
        database.emergencyContactDao().getContactsByUser(userId)
    
    // Safe Zones
    suspend fun addSafeZone(safeZone: SafeZone) = 
        database.safeZoneDao().insert(safeZone)
    
    fun getSafeZonesByType(type: SafeZone.ZoneType) = 
        database.safeZoneDao().getSafeZonesByType(type)
    
    fun getSafeZonesInArea(
        southWestLat: Double,
        southWestLng: Double,
        northEastLat: Double,
        northEastLng: Double
    ) = database.safeZoneDao().getSafeZonesInArea(
        southWestLat, southWestLng, northEastLat, northEastLng
    )
    
    // Unsafe Zones
    suspend fun reportUnsafeZone(unsafeZone: UnsafeZone) = 
        database.unsafeZoneDao().insert(unsafeZone)
    
    fun getUnsafeZonesInArea(
        southWestLat: Double,
        southWestLng: Double,
        northEastLat: Double,
        northEastLng: Double
    ) = database.unsafeZoneDao().getUnsafeZonesInArea(
        southWestLat, southWestLng, northEastLat, northEastLng
    )
    
    // Message Logs
    suspend fun logMessage(messageLog: MessageLog) = 
        database.messageLogDao().insert(messageLog)
    
    fun getMessageHistory(userId: String) = 
        database.messageLogDao().getMessagesByUser(userId)
    
    // Guardian Acknowledgments
    suspend fun logGuardianAck(ack: GuardianAck) = 
        database.guardianAckDao().insert(ack)
    
    fun getAcksForMessage(messageId: Long) = 
        database.guardianAckDao().getAcksForMessage(messageId)
    
    companion object {
        @Volatile
        private var INSTANCE: SafetyRepository? = null
        
        fun getInstance(context: Context): SafetyRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = SafetyRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
