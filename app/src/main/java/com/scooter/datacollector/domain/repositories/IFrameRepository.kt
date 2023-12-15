package com.scooter.datacollector.domain.repositories

import com.scooter.datacollector.domain.models.Frame

interface IFrameRepository {
    fun saveFrame(frame: Frame)

}