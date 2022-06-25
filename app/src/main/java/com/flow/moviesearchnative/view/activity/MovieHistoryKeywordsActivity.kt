package com.flow.moviesearchnative.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.flow.moviesearchnative.MovieHistoryKeywordsItem
import com.flow.moviesearchnative.networkService.MovieHistoryKeywordsNetworkService
import com.flow.moviesearchnative.databinding.MovieHistoryKeywordsBinding
import com.flow.moviesearchnative.viewModel.MovieHistoryKeywordsViewModel
import com.flow.moviesearchnative.viewModel.MovieListViewModel
import com.wefika.flowlayout.FlowLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieHistoryKeywordsActivity : AppCompatActivity() {
    private lateinit var binding: MovieHistoryKeywordsBinding
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= MovieHistoryKeywordsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        apiGetMovieHistoryKeywords()
    }

    private fun apiGetMovieHistoryKeywords(){
        val vm = MovieHistoryKeywordsViewModel(application)
        vm.apiGetMovieHistoryKeywords(binding)

        vm.items.observe(this, Observer {
            it.forEach {
                addButton(it.keywords)
            }
        })
    }

    private fun addButton (title : String) {
        val buttonView = binding.buttonview
        val dynamicButton = Button(context)

        val layoutParams = FlowLayout.LayoutParams(
            FlowLayout.LayoutParams.WRAP_CONTENT,
            FlowLayout.LayoutParams.WRAP_CONTENT,
        )

        dynamicButton.layoutParams = layoutParams
        dynamicButton.text = title
        layoutParams.setMargins(changeDP(5), changeDP(5), changeDP(5), 0)

        buttonView.addView(dynamicButton)

        dynamicButton.setOnClickListener {
            val myIntent = Intent(context, MainActivity::class.java)
            myIntent.putExtra("data", title)
            setResult(Activity.RESULT_OK, myIntent)
            finish()
        }
    }

    fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }
/*
    private fun apiGetMovieHistoryKeywords(){

        val baseUrl = "http://125.128.10.133:8080" // root 주소

        // retrofit2 api를 통해서 api 호출
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 통신 규칙 정하여 서비스 정의
        val service = retrofit.create(MovieHistoryKeywordsNetworkService::class.java)

        // api 통신
        val call = service.getRequestMsg()

        // 받아온 값에 따라 분기함
        call.enqueue(object : Callback<MutableList<MovieHistoryKeywordsItem>> {
            // 실패하였을 경우
            override fun onFailure(call: Call<MutableList<MovieHistoryKeywordsItem>>, t: Throwable) {
                t.printStackTrace()
            }
            // 성공하였을 경우
            override fun onResponse(call: Call<MutableList<MovieHistoryKeywordsItem>>, response: Response<MutableList<MovieHistoryKeywordsItem>>) {
                val items = response.body()!!

                // 2. api 처리 후 데이터를 리스트화함
                items.forEach {
                    addButton(it.keywords)
                }
                // 3. 리스트 항목 클릭했을 때 이벤트
            }

            private fun addButton (title : String) {
                val buttonView = binding.buttonview
                val dynamicButton = Button(context)

                val layoutParams = FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT,
                    FlowLayout.LayoutParams.WRAP_CONTENT,
                )

                dynamicButton.layoutParams = layoutParams
                dynamicButton.text = title
                layoutParams.setMargins(changeDP(5), changeDP(5), changeDP(5), 0)

                buttonView.addView(dynamicButton)

                dynamicButton.setOnClickListener {
                    val myIntent = Intent(context, MainActivity::class.java)
                    myIntent.putExtra("data", title)
                    setResult(Activity.RESULT_OK, myIntent)
                    finish()
                }
            }

            fun changeDP(value : Int) : Int{
                var displayMetrics = resources.displayMetrics
                var dp = Math.round(value * displayMetrics.density)
                return dp
            }
        })
    }

 */
}