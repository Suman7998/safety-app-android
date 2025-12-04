package com.safety.womensafetyapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.safety.womensafetyapp.data.model.GuardianAck

@Dao
interface GuardianAckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ack: GuardianAck): Long

    @Update
    suspend fun update(ack: GuardianAck)

    @Delete
    suspend fun delete(ack: GuardianAck)

    @Query("SELECT * FROM guardian_acks WHERE id = :id")
    suspend fun getAckById(id: Long): GuardianAck?

    @Query("""
        SELECT * FROM guardian_acks 
        WHERE messageId = :messageId
        ORDER BY ackTime DESC
    """)
    fun getAcksForMessage(messageId: Long): LiveData<List<GuardianAck>>

    @Query("""
        SELECT * FROM guardian_acks 
        WHERE guardianId = :guardianId
        ORDER BY ackTime DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getAcksForGuardian(
        guardianId: Long,
        limit: Int = 50,
        offset: Int = 0
    ): LiveData<List<GuardianAck>>

    @Query("""
        SELECT * FROM guardian_acks 
        WHERE messageId = :messageId 
        AND guardianId = :guardianId
        LIMIT 1
    """)
    suspend fun getAck(messageId: Long, guardianId: Long): GuardianAck?

    @Query("""
        UPDATE guardian_acks 
        SET status = :newStatus,
            ackTime = :ackTime,
            message = :message
        WHERE id = :ackId
    """)
    suspend fun updateAckStatus(
        ackId: Long,
        newStatus: GuardianAck.AckStatus,
        ackTime: Long = System.currentTimeMillis(),
        message: String? = null
    )

    @Query("""
        SELECT COUNT(*) FROM guardian_acks 
        WHERE messageId = :messageId 
        AND status = :status
    """)
    suspend fun countAcksByStatus(
        messageId: Long,
        status: GuardianAck.AckStatus
    ): Int
}
