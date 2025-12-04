package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "guardian_acks",
    foreignKeys = [
        ForeignKey(
            entity = MessageLog::class,
            parentColumns = ["id"],
            childColumns = ["messageId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EmergencyContact::class,
            parentColumns = ["id"],
            childColumns = ["guardianId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("messageId"),
        Index("guardianId")
    ]
)
data class GuardianAck(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val messageId: Long, // Reference to MessageLog
    val guardianId: Long, // Reference to EmergencyContact
    val ackTime: Long = System.currentTimeMillis(),
    val status: AckStatus,
    val message: String? = null
) {
    enum class AckStatus {
        RECEIVED, VIEWED, RESPONDED, IGNORED
    }
}
