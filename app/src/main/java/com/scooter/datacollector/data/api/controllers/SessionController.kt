package com.scooter.datacollector.data.api.controllers

import com.scooter.datacollector.data.api.models.Session
import com.scooter.datacollector.data.api.requestModels.GetFramesCountInSessionRequest
import com.scooter.datacollector.data.api.requestModels.GetFramesCountInSessionResponse
import com.scooter.datacollector.data.api.requestModels.SaveSessionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SessionController {
    @POST("/SaveSession")
    fun saveSession(@Body request: SaveSessionRequest) : Call<Unit>

    @POST("/GetFramesCount")
    fun getFramesCountInSession(@Body request: Session) : Call<Int>
}