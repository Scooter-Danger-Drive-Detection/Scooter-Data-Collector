package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.SessionController

class EndSessionUsecase(private val sessionController: SessionController) {
    public fun execute() = sessionController.endSession()
}