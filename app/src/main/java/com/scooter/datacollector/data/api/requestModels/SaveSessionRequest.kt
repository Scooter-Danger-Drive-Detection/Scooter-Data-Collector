package com.scooter.datacollector.data.api.requestModels

import com.scooter.datacollector.data.api.models.Frame
import com.scooter.datacollector.data.api.models.Session

data class SaveSessionRequest(
    val Session: Session,
    val Frames: List<Frame>
)