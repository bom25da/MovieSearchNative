package com.flow.moviesearchnative

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.flow.moviesearchnative.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val items = mutableListOf<MovieListItem>(
//            MovieListItem("https://ssl.pstatic.net/imgmovie/mdi/mit110/2109/210934_P01_152544.jpg", "무간도1", "2020", "9.00"),
//            MovieListItem("https://ssl.pstatic.net/imgmovie/mdi/mit110/2109/210934_P01_152544.jpg", "무간도2", "2021", "8.00"),
//            MovieListItem("https://ssl.pstatic.net/imgmovie/mdi/mit110/2109/210934_P01_152544.jpg", "무간도3", "2022", "6.00")
//        )
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val keywords = result.data?.getStringExtra("data")

                if (keywords != null) {
                    apiGetNaverMovieSearchData(keywords)
                }
            }
        }

        // 검색 버튼 눌렀을 때 이벤트
        binding.btnSearchMovie.setOnClickListener {

            var keywords = binding.etSearchMovie.text.toString()

            apiGetNaverMovieSearchData(keywords)
            apiInsertHistoryKeywords(keywords)
        }

        // 검색기록 눌렀을 때 이벤트
        binding.btnSearchHistory.setOnClickListener {
            val myIntent = Intent(applicationContext, MovieHistoryKeywordsActivity::class.java)
            resultLauncher.launch(myIntent)
        }
    }

    private fun apiGetNaverMovieSearchData(keywords: String){

        val baseUrl = "https://openapi.naver.com" // root 주소
        val apiId = "dUPa2H00gMoXPiq7A0ID" // naver api id
        val apiPass = "0eeiCeiNzZ" // naver api password
        val searchKeywords = keywords // 검색 키워드 바인딩해서 불러옴
        var rtnItems = mutableListOf<MovieListItem>()

        // retrofit2 api를 통해서 api 호출
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 통신 규칙 정하여 서비스 정의
        val service = retrofit.create(MovieListNetworkService::class.java)

        // api 통신
        val call = service.requestAuthMsg(apiId, apiPass, searchKeywords)

        // 받아온 값에 따라 분기함
        call.enqueue(object : Callback<MovieList>{
            // 실패하였을 경우
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                t.printStackTrace()
            }
            // 성공하였을 경우
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {

                Log.d("success", response.body()!!.items.toString())

                val items = response.body()!!.items
                Log.d("success", "0")

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

                        val myIntent = Intent(applicationContext, MovieDetailActivity::class.java)
                        myIntent.putExtra("data", items[position].link)
                        startActivity(myIntent)
                    }
                })
            }
        })
    }

    private fun apiInsertHistoryKeywords(keywords: String) {
        val culTime = LocalDateTime.now()
        val culTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val keywordsInputTime = culTime.format(culTimeFormatter)

        val baseUrl = "http://125.128.10.133:8080" // root 주소
        var postBody = MovieHistoryKeywordsItem(1, keywords, keywordsInputTime)

        // retrofit2 api를 통해서 api 호출
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 통신 규칙 정하여 서비스 정의
        val service = retrofit.create(MovieHistoryKeywordsNetworkService::class.java)

        // api 통신
        val call = service.requestAuthMsg(postBody)

        // 받아온 값에 따라 분기함
        call.enqueue(object : Callback<MovieHistoryKeywordsResult>{
            // 실패하였을 경우
            override fun onFailure(call: Call<MovieHistoryKeywordsResult>, t: Throwable) {t.printStackTrace()}
            // 성공하였을 경우
            override fun onResponse(call: Call<MovieHistoryKeywordsResult>, response: Response<MovieHistoryKeywordsResult>) {Log.d("result:", response.body()!!.toString())}
        })

    }
}