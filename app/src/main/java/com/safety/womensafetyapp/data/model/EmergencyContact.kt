package com.safety.womensafetyapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "emergency_contacts",
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
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String, // Foreign key to User
    val name: String,
    val phone: String,
    val email: String? = null,
    val relation: String? = null,
    val isPrimary: Boolean = false
)
