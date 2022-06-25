package com.flow.moviesearchnative.repositories

import com.flow.moviesearchnative.MovieHistoryKeywordsItem
import com.flow.moviesearchnative.MovieHistoryKeywordsResult
import com.flow.moviesearchnative.networkService.MovieHistoryKeywordsNetworkService
import retrofit2.Call

class MovieHistoryKewordsRepositoy {
    private val api = MovieHistoryKeywordsNetworkService.create()

    fun postMovieHistoryKeywordsRepo(postBody : MovieHistoryKeywordsItem)
            : Call<MovieHistoryKeywordsResult> = api.postRequestMsg(postBody)

    fun getMovieHistoryKeywordsRepo()
            : Call<MutableList<MovieHistoryKeywordsItem>> = api.getRequestMsg()
}