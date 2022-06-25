package com.flow.moviesearchnative.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.flow.moviesearchnative.MovieList
import com.flow.moviesearchnative.databinding.ActivityMainBinding
import com.flow.moviesearchnative.repositories.MovieListRepository
import com.flow.moviesearchnative.view.adapter.MovieListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel(application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext

    fun apiGetNaverMovieSearchData(searchKeywords : String, binding : ActivityMainBinding) {
        val apiId = "dUPa2H00gMoXPiq7A0ID" // naver api id
        val apiPass = "0eeiCeiNzZ" // naver api password

        val repo = MovieListRepository()
        val call = repo.getMovieListRepo(apiId, apiPass, searchKeywords)

        call.enqueue(object : Callback<MovieList> {
            // 실패하였을 경우
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                t.printStackTrace()
            }
            // 성공하였을 경우
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                val items = response.body()!!.items

                // 2. api 처리 후 데이터를 리스트화함
                val mAdapter = MovieListAdapter(context, items)
                binding.rv1.adapter = mAdapter
                val layout = LinearLayoutManager(context)
                binding.rv1.layoutManager = layout
                binding.rv1.setHasFixedSize(true)

                // 3. 리스트 항목 클릭했을 때 이벤트
                mAdapter.setMyItemClickListener(object : MovieListAdapter.MyItemClickListener {
                    override fun onItemClick(position: Int) {
                        Log.d("Link:", items[position].link)

                        val webView = binding.webView
                        webView.loadUrl(items[position].link)
                    }
                })
            }
        })
    }
}