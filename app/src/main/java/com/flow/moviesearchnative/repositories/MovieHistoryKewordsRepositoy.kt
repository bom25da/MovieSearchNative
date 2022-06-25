/*
    Category    : Repositories
    Title       : 영화 검색 기록 레포지토리
    Description : 네트워크 서비스와 뷰모델 사이를 연결
*/

package com.flow.moviesearchnative.repositories

import com.flow.moviesearchnative.MovieHistoryKeywordsItem
import com.flow.moviesearchnative.MovieHistoryKeywordsResult
import com.flow.moviesearchnative.networkService.MovieHistoryKeywordsNetworkService
import retrofit2.Call

class MovieHistoryKewordsRepositoy {
    // 네트워크 서비스 생성
    private val api = MovieHistoryKeywordsNetworkService.create()

    // View Model에서 사용하기 위해 함수 구현
    fun postMovieHistoryKeywordsRepo(postBody : MovieHistoryKeywordsItem)
            : Call<MovieHistoryKeywordsResult> = api.postRequestMsg(postBody)

    // View Model에서 사용하기 위해 함수 구현
    fun getMovieHistoryKeywordsRepo()
            : Call<MutableList<MovieHistoryKeywordsItem>> = api.getRequestMsg()
}