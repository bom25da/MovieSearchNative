package com.flow.moviesearchnative

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieHistoryKeywordsNetworkService {
    @POST("/keywords/create")
    fun requestAuthMsg(
        @Body jsonParams: MovieHistoryKeywordsItem
    ): Call<MovieHistoryKeywordsResult>

    @GET("/keywords/read")
    fun getRequestMsg(
    ): Call<MutableList<MovieHistoryKeywordsItem>>
}