package com.scooter.datacollector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scooter.datacollector.data.local.dao.FrameDao
import com.scooter.datacollector.data.local.dao.SessionDao
import com.scooter.datacollector.data.local.entities.FrameEntity
import com.scooter.datacollector.data.local.entities.SessionEntity

@Database(entities = [SessionEntity::class, FrameEntity::class], version = 2)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun frameDao() : FrameDao
    abstract fun sessionDao(): SessionDao
}