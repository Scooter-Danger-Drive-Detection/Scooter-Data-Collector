package com.scooter.datacollector.data

import com.scooter.datacollector.data.api.controllers.RideSafetyController
import com.scooter.datacollector.domain.IRideSafety
import com.scooter.datacollector.domain.models.Session

class RideSafety(private val rideSafetyController: RideSafetyController) : IRideSafety {
    override fun get(session: Session): Double {
        return rideSafetyController
            .getPredict(com.scooter.datacollector.data.api.models.Session(
                session.id,
                session.userId,
                session.rideMode.ordinal
            )).execute().body()!!
    }
}