package com.scooter.datacollector.sessionstate

import com.scooter.datacollector.domain.IFrameDataCollector
import com.scooter.datacollector.domain.ISessionController
import com.scooter.datacollector.domain.auth.IAuth
import com.scooter.datacollector.domain.models.RideMode
import com.scooter.datacollector.domain.models.Session
import com.scooter.datacollector.domain.repositories.ISessionRepository

class SessionController(private val auth: IAuth, private val sessionRepository: ISessionRepository, private val frameDataCollector: IFrameDataCollector) :
    ISessionController {
    private var currentSession: Session? = null

    override fun isSessionStarted(): Boolean =
        currentSession != null

    override fun startSession(rideMode: RideMode) {
        currentSession = Session(
            id = sessionRepository.getIdForNewSession(),
            usedId = auth.getCurrentUserId(),
            rideMode = rideMode,
        )
        sessionRepository.createSession(currentSession!!)
        frameDataCollector.startFrameDataCollection(currentSession!!.id)
    }

    override fun endSession() {
        frameDataCollector.stopFrameDataCollection()
        currentSession = null
    }

    override fun getCurrentSession(): Session = currentSession!!
}