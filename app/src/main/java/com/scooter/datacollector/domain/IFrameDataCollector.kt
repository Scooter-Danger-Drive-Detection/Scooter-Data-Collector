package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.models.Frame

interface IFrameDataCollector {
    public fun startFrameDataCollection()
    public fun stopFrameDataCollection()
    public fun subscribeOnFrameUpdate(callback: (Frame) -> Unit)
    public fun unsubscribeOnFrameUpdate(callback: (Frame) -> Unit)
}