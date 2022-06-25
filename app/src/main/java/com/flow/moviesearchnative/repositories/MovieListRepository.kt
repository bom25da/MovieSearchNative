package com.flow.moviesearchnative.repositories

import retrofit2.Call
import com.flow.moviesearchnative.MovieList
import com.flow.moviesearchnative.networkService.MovieListNetworkService

class MovieListRepository {
    private val api = MovieListNetworkService.create()

    fun getMovieListRepo(apiId : String, apiPass : String, searchKeywords : String)
        : Call<MovieList> = api.getRequestMsg(apiId, apiPass, searchKeywords)
}