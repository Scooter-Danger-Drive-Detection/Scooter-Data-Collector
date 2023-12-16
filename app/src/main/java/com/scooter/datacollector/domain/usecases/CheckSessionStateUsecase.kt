package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.sessionstate.SessionController

class CheckSessionStateUsecase(private val sessionController: SessionController) {
    public fun execute() = true // TODO
}