/*
    Category    : Service
    Title       : API 요청 Service
    Description : API에 전송정보를 담아서 Rest API 방식으로 데이터를 요청
*/

package com.flow.moviesearchnative.networkService

import com.flow.moviesearchnative.MovieHistoryKeywordsItem
import com.flow.moviesearchnative.MovieHistoryKeywordsResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieHistoryKeywordsNetworkService {
    // 영화 키워드 검색 기록 서버에 기록하는 함수
    @POST("/keywords/create")
    fun postRequestMsg(
        @Body jsonParams: MovieHistoryKeywordsItem
    ): Call<MovieHistoryKeywordsResult>

    // 영화 키워드 검색 내역 조회하는 함수
    @GET("/keywords/read")
    fun getRequestMsg(
    ): Call<MutableList<MovieHistoryKeywordsItem>>

    companion object {
        val baseUrl = "http://125.128.10.133:8080" // root 주소

        // 네트워크 서비스 정의 함수
        fun create() : MovieHistoryKeywordsNetworkService {

            // retrofit2 api를 통해서 api 호출
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // 통신 규칙 정하여 서비스 정의
            return retrofit.create(MovieHistoryKeywordsNetworkService::class.java)
        }
    }
}