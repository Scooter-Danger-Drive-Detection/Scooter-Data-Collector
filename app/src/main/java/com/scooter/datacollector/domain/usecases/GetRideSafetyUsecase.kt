package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.IRideSafety
import com.scooter.datacollector.domain.ISessionController

class GetRideSafetyUsecase(private val sessionController: ISessionController, private val rideSafety: IRideSafety) {
    fun execute(callback: (Double)-> Unit, onError: ()->Unit){
        Thread{
            try {
                val safety = rideSafety.get(sessionController.getCurrentSession())
                callback(safety)
            } catch (e: Exception){
                onError()
            }
        }.start()
    }
}