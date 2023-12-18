package com.scooter.datacollector.data.api.requestModels

import com.scooter.datacollector.data.api.models.Session

data class GetFramesCountInSessionRequest(
    val session: Session
)