package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.IDataSynchronizer

class SynchronizeAllUsecase(private val dataSynchronizer: IDataSynchronizer) {
    fun execute(callback: ()->Unit, onError: ()->Unit){
        Thread{
            try {
                dataSynchronizer.synchronizeAll()
                callback()
            } catch (e: Exception){
                onError()
            }
        }.start()
    }
}