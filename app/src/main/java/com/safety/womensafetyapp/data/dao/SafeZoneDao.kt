package com.safety.womensafetyapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.safety.womensafetyapp.data.model.SafeZone

@Dao
interface SafeZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(safeZone: SafeZone): Long

    @Update
    suspend fun update(safeZone: SafeZone)

    @Delete
    suspend fun delete(safeZone: SafeZone)

    @Query("SELECT * FROM safe_zones WHERE id = :id")
    suspend fun getSafeZoneById(id: Long): SafeZone?

    @Query("SELECT * FROM safe_zones WHERE type = :type ORDER BY name ASC")
    fun getSafeZonesByType(type: SafeZone.ZoneType): LiveData<List<SafeZone>>

    @Query("""
        SELECT * FROM safe_zones 
        WHERE latitude BETWEEN :southWestLat AND :northEastLat 
        AND longitude BETWEEN :southWestLng AND :northEastLng
        ORDER BY 
            CASE 
                WHEN type = 'POLICE_STATION' THEN 1
                WHEN type = 'HOSPITAL' THEN 2
                WHEN type = 'SHELTER' THEN 3
                ELSE 4
            END
    """)
    fun getSafeZonesInArea(
        southWestLat: Double,
        southWestLng: Double,
        northEastLat: Double,
        northEastLng: Double
    ): LiveData<List<SafeZone>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT *, 
        (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * 
        cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * 
        sin(radians(latitude)))) AS distance 
        FROM safe_zones 
        WHERE (:type IS NULL OR type = :type)
        ORDER BY distance ASC
        LIMIT :limit
    """)
    suspend fun getNearestSafeZones(
        lat: Double,
        lng: Double,
        type: SafeZone.ZoneType? = null,
        limit: Int = 10
    ): List<SafeZone>
}
