package com.safety.womensafetyapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.safety.womensafetyapp.data.model.GuardianAck
import com.safety.womensafetyapp.data.model.MessageLog
import com.safety.womensafetyapp.data.model.SafeZone
import com.safety.womensafetyapp.data.model.UnsafeZone
import java.util.*

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromLongList(value: List<Long>?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLongList(value: String?): List<Long>? {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    // Enums
    @TypeConverter
    fun fromZoneType(value: SafeZone.ZoneType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toZoneType(value: String?): SafeZone.ZoneType? {
        return value?.let { SafeZone.ZoneType.valueOf(it) }
    }

    @TypeConverter
    fun fromDangerLevel(value: UnsafeZone.DangerLevel?): String? {
        return value?.name
    }

    @TypeConverter
    fun toDangerLevel(value: String?): UnsafeZone.DangerLevel? {
        return value?.let { UnsafeZone.DangerLevel.valueOf(it) }
    }

    @TypeConverter
    fun fromMessageType(value: MessageLog.MessageType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toMessageType(value: String?): MessageLog.MessageType? {
        return value?.let { MessageLog.MessageType.valueOf(it) }
    }

    @TypeConverter
    fun fromMessageStatus(value: MessageLog.MessageStatus?): String? {
        return value?.name
    }

    @TypeConverter
    fun toMessageStatus(value: String?): MessageLog.MessageStatus? {
        return value?.let { MessageLog.MessageStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromAckStatus(value: GuardianAck.AckStatus?): String? {
        return value?.name
    }

    @TypeConverter
    fun toAckStatus(value: String?): GuardianAck.AckStatus? {
        return value?.let { GuardianAck.AckStatus.valueOf(it) }
    }
}
