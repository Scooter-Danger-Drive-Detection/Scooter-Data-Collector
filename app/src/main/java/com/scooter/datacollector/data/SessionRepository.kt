package com.scooter.datacollector.data

import com.scooter.datacollector.domain.models.Session
import com.scooter.datacollector.domain.repositories.ISessionRepository

class SessionRepository : ISessionRepository {
    override fun getIdForNewSession(): Int {
        return 1
        //TODO("Not yet implemented")
    }

    override fun createSession(session: Session) {
        //TODO("Not yet implemented")
    }
}