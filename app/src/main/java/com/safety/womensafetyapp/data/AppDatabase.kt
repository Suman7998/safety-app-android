package com.safety.womensafetyapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.safety.womensafetyapp.data.dao.*
import com.safety.womensafetyapp.data.model.*
import com.safety.womensafetyapp.util.Converters

@Database(
    entities = [
        User::class,
        EmergencyContact::class,
        SafeZone::class,
        UnsafeZone::class,
        MessageLog::class,
        GuardianAck::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun emergencyContactDao(): EmergencyContactDao
    abstract fun safeZoneDao(): SafeZoneDao
    abstract fun unsafeZoneDao(): UnsafeZoneDao
    abstract fun messageLogDao(): MessageLogDao
    abstract fun guardianAckDao(): GuardianAckDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "womensafety_db"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    // You can add database callbacks here if needed
                })
                .fallbackToDestructiveMigration() // For development only, remove in production
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}
