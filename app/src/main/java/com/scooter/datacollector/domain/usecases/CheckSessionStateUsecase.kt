package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.SessionController

class CheckSessionStateUsecase(private val sessionController: SessionController) {
    public fun execute() = sessionController.isSessionStarted()
}