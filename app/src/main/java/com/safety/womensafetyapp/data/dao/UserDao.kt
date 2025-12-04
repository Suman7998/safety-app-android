package com.safety.womensafetyapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.safety.womensafetyapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeUserById(id: String): LiveData<User?>

    @Query("SELECT * FROM users WHERE phone = :phone")
    suspend fun getUserByPhone(phone: String): User?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE users SET lastActive = :timestamp WHERE id = :userId")
    suspend fun updateLastActive(userId: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE users SET isActive = :isActive WHERE id = :userId")
    suspend fun updateUserActiveStatus(userId: String, isActive: Boolean)

    @Query("UPDATE users SET homeLatitude = :lat, homeLongitude = :lng WHERE id = :userId")
    suspend fun updateHomeLocation(userId: String, lat: Double, lng: Double)

    @Query("UPDATE users SET workLatitude = :lat, workLongitude = :lng WHERE id = :userId")
    suspend fun updateWorkLocation(userId: String, lat: Double, lng: Double)

    @Query("UPDATE users SET isLocationSharingEnabled = :isEnabled WHERE id = :userId")
    suspend fun setLocationSharingEnabled(userId: String, isEnabled: Boolean)

    @Query("UPDATE users SET emergencyMessage = :message WHERE id = :userId")
    suspend fun updateEmergencyMessage(userId: String, message: String)

    @Query("UPDATE users SET sosContacts = :contactIds WHERE id = :userId")
    suspend fun updateSosContacts(userId: String, contactIds: List<Long>)

    @Query("SELECT COUNT(*) FROM users WHERE phone = :phone")
    suspend fun isPhoneRegistered(phone: String): Int

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailRegistered(email: String): Int
}
