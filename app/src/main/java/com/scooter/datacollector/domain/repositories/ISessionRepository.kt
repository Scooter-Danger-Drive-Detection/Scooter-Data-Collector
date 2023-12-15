package com.scooter.datacollector.domain.repositories

import com.scooter.datacollector.domain.models.Session

interface ISessionRepository {
    public fun getIdForNewSession(): Int
    public fun createSession(session: Session)
}