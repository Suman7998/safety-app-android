package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "message_logs",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId")
    ]
)
data class MessageLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String, // User who sent the message
    val messageType: MessageType,
    val timestamp: Long = System.currentTimeMillis(),
    val content: String,
    val recipients: List<String>, // Comma-separated phone numbers or user IDs
    val status: MessageStatus = MessageStatus.PENDING,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locationName: String? = null
) {
    enum class MessageType {
        SOS, CHECK_IN, CUSTOM
    }
    
    enum class MessageStatus {
        PENDING, SENT, DELIVERED, FAILED
    }
}
