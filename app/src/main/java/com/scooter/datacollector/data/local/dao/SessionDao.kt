package com.scooter.datacollector.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.scooter.datacollector.data.local.entities.SessionEntity

@Dao
interface SessionDao {
    @Insert
    fun insert(sessionEntity: SessionEntity)

    @Query("SELECT MAX(id) FROM sessions")
    fun nextSessionId(): Int
}