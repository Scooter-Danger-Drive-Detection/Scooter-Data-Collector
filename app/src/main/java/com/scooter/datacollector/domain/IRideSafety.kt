package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.models.Session

interface IRideSafety {
    fun get(session: Session) : Double
}