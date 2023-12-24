package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.models.Session

interface IDataSynchronizer {
    fun synchronizeAll()
    fun synchronizeSession(session: Session)
}