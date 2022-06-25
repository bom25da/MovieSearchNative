/*
    Category    : Service
    Title       : API 요청 Service
    Description : API에 전송정보를 담아서 Rest API 방식으로 데이터를 요청
*/

package com.flow.moviesearchnative.networkService

import com.flow.moviesearchnative.MovieList
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MovieListNetworkService {
    // 네이버 영화 API에서 데이터 요청
    @GET("/v1/search/movie.json")
    fun getRequestMsg(
        @Header("X-Naver-Client-Id") apiId:String,
        @Header("X-Naver-Client-Secret") apiPass:String,
        @Query("query") inputText:String,
    ): Call<MovieList>

    companion object {
        val baseUrl = "https://openapi.naver.com" // root 주소

        // 네트워크 서비스 정의 함수
        fun create() : MovieListNetworkService {

            // retrofit2 api를 통해서 api 호출
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // 통신 규칙 정하여 서비스 정의
            return retrofit.create(MovieListNetworkService::class.java)
        }
    }
}