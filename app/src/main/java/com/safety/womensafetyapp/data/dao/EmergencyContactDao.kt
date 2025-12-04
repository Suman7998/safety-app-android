package com.safety.womensafetyapp.data.dao

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import androidx.room.*
import com.safety.womensafetyapp.data.model.EmergencyContact

@Dao
interface EmergencyContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: EmergencyContact): Long

    @Update
    suspend fun update(contact: EmergencyContact)

    @Delete
    suspend fun delete(contact: EmergencyContact)

    @Query("SELECT * FROM emergency_contacts WHERE id = :id")
    suspend fun getContactById(id: Long): EmergencyContact?

    @Query("SELECT * FROM emergency_contacts WHERE userId = :userId ORDER BY isPrimary DESC, name ASC")
    fun getContactsByUser(userId: String): Flow<List<EmergencyContact>>

    @Query("SELECT * FROM emergency_contacts WHERE userId = :userId AND isPrimary = 1 LIMIT 1")
    suspend fun getPrimaryContact(userId: String): EmergencyContact?

    @Query("UPDATE emergency_contacts SET isPrimary = 0 WHERE userId = :userId")
    suspend fun clearPrimaryFlags(userId: String)

    @Query("SELECT COUNT(*) FROM emergency_contacts WHERE userId = :userId")
    suspend fun getContactCount(userId: String): Int
}
