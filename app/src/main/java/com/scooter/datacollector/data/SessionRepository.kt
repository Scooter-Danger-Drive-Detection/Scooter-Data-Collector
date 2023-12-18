package com.scooter.datacollector.data

import androidx.loader.content.AsyncTaskLoader
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.data.local.entities.SessionEntity
import com.scooter.datacollector.domain.models.Session
import com.scooter.datacollector.domain.repositories.ISessionRepository
import java.util.Date
import java.util.concurrent.Executor

class SessionRepository(private val localDatabase: LocalDatabase) : ISessionRepository {
    override fun getIdForNewSession(): Long
         = Date().time


    override fun createSession(session: Session) {
        Thread{
            localDatabase.sessionDao().insert( SessionEntity(
                    session.id,
                    session.usedId,
                    session.rideMode
                )
            )
        }.start()
    }
}