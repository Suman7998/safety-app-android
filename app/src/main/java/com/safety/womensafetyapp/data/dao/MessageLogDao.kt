package com.safety.womensafetyapp.data.dao

import androidx.room.*
import com.safety.womensafetyapp.data.model.MessageLog
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(messageLog: MessageLog): Long

    @Update
    suspend fun update(messageLog: MessageLog)

    @Delete
    suspend fun delete(messageLog: MessageLog)

    @Query("SELECT * FROM message_logs WHERE id = :id")
    suspend fun getMessageById(id: Long): MessageLog?

    @Query("""
        SELECT * FROM message_logs 
        WHERE userId = :userId 
        ORDER BY timestamp DESC
        LIMIT :limit OFFSET :offset
    """)
    fun getMessagesByUser(
        userId: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<List<MessageLog>>

    @Query("""
        SELECT * FROM message_logs 
        WHERE userId = :userId 
        AND status = :status
        ORDER BY timestamp DESC
    """)
    suspend fun getMessagesByStatus(
        userId: String,
        status: MessageLog.MessageStatus
    ): List<MessageLog>

    @Query("""
        SELECT * FROM message_logs 
        WHERE userId = :userId 
        AND messageType = :messageType
        ORDER BY timestamp DESC
        LIMIT :limit
    """)
    suspend fun getMessagesByType(
        userId: String,
        messageType: MessageLog.MessageType,
        limit: Int = 20
    ): List<MessageLog>

    @Query("""
        UPDATE message_logs 
        SET status = :newStatus 
        WHERE id = :messageId
    """)
    suspend fun updateMessageStatus(
        messageId: Long,
        newStatus: MessageLog.MessageStatus
    )
}
