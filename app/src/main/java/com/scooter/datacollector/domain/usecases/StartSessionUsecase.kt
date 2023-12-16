package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.sessionstate.SessionController
import com.scooter.datacollector.domain.models.RideMode

class StartSessionUsecase(private val sessionController: SessionController) {
    public fun execute(rideMode: RideMode) = sessionController.startSession(rideMode)

}