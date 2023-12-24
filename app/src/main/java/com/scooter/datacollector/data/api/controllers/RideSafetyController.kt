package com.scooter.datacollector.data.api.controllers

import com.scooter.datacollector.data.api.models.Session
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RideSafetyController {
    @POST("/Predict")
    fun getPredict(@Body session: Session) : Call<Double>
}