/*
    Category    : Activity
    Title       : 검색 기록 화면
    Description : 검색 기록 화면 처리
*/

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

        // 검색 기록 받아오는 함수
        apiGetMovieHistoryKeywords()
    }

    private fun apiGetMovieHistoryKeywords(){
        // 검색 기록 뷰모델 변수 생성
        val vm = MovieHistoryKeywordsViewModel(application)
        // 검색 기록 결과 받아옴
        vm.apiGetMovieHistoryKeywords(binding)

        // 검색 결과가 나올때 까지 기다렸다가 실행함(LiveData 옵저버 기능 사용)
        vm.items.observe(this, Observer {
            // 검색 결과가 나오면 버튼을 추가해줌
            it.forEach {
                addButton(it.keywords)
            }
        })
    }

    // 검색기록 버튼 동적 추가 함수
    private fun addButton (title : String) {
        val buttonView = binding.buttonview
        // 버튼 객체 생성
        val dynamicButton = Button(context)

        // 버튼 레이아웃 변수 생성
        val layoutParams = FlowLayout.LayoutParams(
            FlowLayout.LayoutParams.WRAP_CONTENT,
            FlowLayout.LayoutParams.WRAP_CONTENT,
        )

        // 버튼에 레이아웃 정의해줌
        dynamicButton.layoutParams = layoutParams
        // 버튼 텍스트는 검색기록으로 셋팅
        dynamicButton.text = title
        // 버튼에 마진값 줌
        layoutParams.setMargins(changeDP(5), changeDP(5), changeDP(5), 0)

        // 뷰에 추가함
        buttonView.addView(dynamicButton)

        // 버튼 클릭했을 때
        dynamicButton.setOnClickListener {
            // 검색기록 결과 가지고 메인 액티비티로 이동함
            val myIntent = Intent(context, MainActivity::class.java)
            myIntent.putExtra("data", title)
            setResult(Activity.RESULT_OK, myIntent)
            // 메인 액티비티로 이동하면 현재 액티비티 스레드 종료
            finish()
        }
    }

    // 마진값 dp로 변환시켜주는 함수
    fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }
}