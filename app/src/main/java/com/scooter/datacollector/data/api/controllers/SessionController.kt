package com.scooter.datacollector.data.api.controllers

import com.scooter.datacollector.data.api.requestModels.GetFramesCountInSessionRequest
import com.scooter.datacollector.data.api.requestModels.GetFramesCountInSessionResponse
import com.scooter.datacollector.data.api.requestModels.SaveSessionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SessionController {
    @POST("SaveSessionData")
    fun saveSession(request: SaveSessionRequest)

    @POST("GetFramesCount")
    fun getFramesCountInSession(@Body request: GetFramesCountInSessionRequest) : Call<GetFramesCountInSessionResponse>
}