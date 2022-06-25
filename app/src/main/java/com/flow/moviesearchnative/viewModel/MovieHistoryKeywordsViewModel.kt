package com.flow.moviesearchnative.viewModel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flow.moviesearchnative.MovieHistoryKeywordsItem
import com.flow.moviesearchnative.MovieHistoryKeywordsResult
import com.flow.moviesearchnative.databinding.ActivityMainBinding
import com.flow.moviesearchnative.databinding.MovieHistoryKeywordsBinding
import com.flow.moviesearchnative.repositories.MovieHistoryKewordsRepositoy
import com.flow.moviesearchnative.repositories.MovieListRepository
import com.flow.moviesearchnative.view.activity.MainActivity
import com.wefika.flowlayout.FlowLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieHistoryKeywordsViewModel(application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext
    val items = MutableLiveData<MutableList<MovieHistoryKeywordsItem>>()

    fun apiInsertHistoryKeywords(keywords: String) {
        val culTime = LocalDateTime.now()
        val culTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val keywordsInputTime = culTime.format(culTimeFormatter)

        var postBody = MovieHistoryKeywordsItem(1, keywords, keywordsInputTime)

        // api 통신
        val repo = MovieHistoryKewordsRepositoy()
        val call = repo.postMovieHistoryKeywordsRepo(postBody)

        // 받아온 값에 따라 분기함
        call.enqueue(object : Callback<MovieHistoryKeywordsResult> {
            // 실패하였을 경우
            override fun onFailure(call: Call<MovieHistoryKeywordsResult>, t: Throwable) {t.printStackTrace()}
            // 성공하였을 경우
            override fun onResponse(call: Call<MovieHistoryKeywordsResult>, response: Response<MovieHistoryKeywordsResult>) {
                Log.d("result:", response.body()!!.toString())}
        })

    }

    fun apiGetMovieHistoryKeywords(binding : MovieHistoryKeywordsBinding) {

        val repo = MovieHistoryKewordsRepositoy()
        val call = repo.getMovieHistoryKeywordsRepo()

        // 받아온 값에 따라 분기함
        call.enqueue(object : Callback<MutableList<MovieHistoryKeywordsItem>> {
            // 실패하였을 경우
            override fun onFailure(call: Call<MutableList<MovieHistoryKeywordsItem>>, t: Throwable) {
                t.printStackTrace()
            }
            // 성공하였을 경우
            override fun onResponse(call: Call<MutableList<MovieHistoryKeywordsItem>>, response: Response<MutableList<MovieHistoryKeywordsItem>>) {
                items.value = response.body()!!
            }
        })
    }
}