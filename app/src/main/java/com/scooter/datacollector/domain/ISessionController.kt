package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.models.RideMode
import com.scooter.datacollector.domain.models.Session

interface ISessionController {
    public fun isSessionStarted() : Boolean
    public fun startSession(rideMode: RideMode)
    public fun endSession()
    public fun getCurrentSession() : Session

}