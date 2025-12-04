package com.safety.womensafetyapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.safety.womensafetyapp.data.model.UnsafeZone

@Dao
interface UnsafeZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(unsafeZone: UnsafeZone): Long

    @Update
    suspend fun update(unsafeZone: UnsafeZone)

    @Delete
    suspend fun delete(unsafeZone: UnsafeZone)

    @Query("SELECT * FROM unsafe_zones WHERE id = :id")
    suspend fun getUnsafeZoneById(id: Long): UnsafeZone?

    @Query("""
        SELECT * FROM unsafe_zones 
        WHERE latitude BETWEEN :southWestLat AND :northEastLat 
        AND longitude BETWEEN :southWestLng AND :northEastLng
        AND reportDate >= :fromTimestamp
        ORDER BY dangerLevel DESC, reportDate DESC
    """)
    fun getUnsafeZonesInArea(
        southWestLat: Double,
        southWestLng: Double,
        northEastLat: Double,
        northEastLng: Double,
        fromTimestamp: Long = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000) // Last 30 days
    ): LiveData<List<UnsafeZone>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT * FROM (
            SELECT *, 
            (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * 
            cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * 
            sin(radians(latitude)))) AS distance 
            FROM unsafe_zones 
            WHERE reportDate >= :fromTimestamp
        )
        WHERE distance <= :radiusKm
        ORDER BY distance ASC
        LIMIT :limit
    """)
    suspend fun getNearestUnsafeZones(
        lat: Double,
        lng: Double,
        radiusKm: Double = 5.0,
        fromTimestamp: Long = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000), // Last 30 days
        limit: Int = 20
    ): List<UnsafeZone>
}
