package com.scooter.datacollector.domain

interface ISessionController {
    public fun isSessionStarted() : Boolean
    public fun startSession()
    public fun endSession()
    public fun getCurrentSessions()

}