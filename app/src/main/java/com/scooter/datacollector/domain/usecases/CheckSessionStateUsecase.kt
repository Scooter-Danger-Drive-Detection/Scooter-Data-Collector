package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.ISessionController

class CheckSessionStateUsecase(private val sessionController: ISessionController) {
    public fun execute() = sessionController.isSessionStarted()

}