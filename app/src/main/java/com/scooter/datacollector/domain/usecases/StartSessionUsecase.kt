package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.ISessionController
import com.scooter.datacollector.domain.models.RideMode

class StartSessionUsecase(private val sessionController: ISessionController) {
    public fun execute(rideMode: RideMode) = sessionController.startSession(rideMode)

}