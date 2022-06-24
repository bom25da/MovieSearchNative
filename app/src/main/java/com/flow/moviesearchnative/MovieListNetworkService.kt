package com.flow.moviesearchnative

import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call
import java.util.*

interface MovieListNetworkService {
    @GET("/v1/search/movie.json")
    fun requestAuthMsg(
        @Header("X-Naver-Client-Id") apiId:String,
        @Header("X-Naver-Client-Secret") apiPass:String,
        @Query("query") inputText:String,
    ): Call<MovieList>
}