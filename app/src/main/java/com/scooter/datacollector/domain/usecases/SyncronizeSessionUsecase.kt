package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.IDataSynchronizer
import com.scooter.datacollector.domain.models.Session

class SyncronizeSessionUsecase(private val dataSynchronizer: IDataSynchronizer) {
    fun execute(session: Session, callback:()->Unit,onError: () -> Unit){
        try {
            dataSynchronizer.synchronizeSession(session)
            callback()
        } catch(_: Exception){
            onError()
        }
    }
}