/*
    Category    : Repositories
    Title       : 영화 검색 리스트 레포지토리
    Description : 네트워크 서비스와 뷰모델 사이를 연결
*/

package com.flow.moviesearchnative.repositories

import retrofit2.Call
import com.flow.moviesearchnative.MovieList
import com.flow.moviesearchnative.networkService.MovieListNetworkService

class MovieListRepository {
    // 네트워크 서비스 생성
    private val api = MovieListNetworkService.create()

    // View Model에서 사용하기 위해 함수 구현
    fun getMovieListRepo(apiId : String, apiPass : String, searchKeywords : String)
        : Call<MovieList> = api.getRequestMsg(apiId, apiPass, searchKeywords)
}