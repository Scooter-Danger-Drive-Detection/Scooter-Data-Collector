package com.scooter.datacollector.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.scooter.datacollector.data.local.entities.FrameEntity

@Dao
interface FrameDao {
    @Insert
    fun insertFrame(frameEntity: FrameEntity)
    @Query("SELECT * FROM frames")
    fun getAll(): List<FrameEntity>
}