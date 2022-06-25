/*
    Category    : Activity
    Title       : 메인 화면
    Description : 메인 화면 처리
*/

package com.flow.moviesearchnative.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import com.flow.moviesearchnative.databinding.ActivityMainBinding
import com.flow.moviesearchnative.viewModel.MovieHistoryKeywordsViewModel
import com.flow.moviesearchnative.viewModel.MovieListViewModel

class MainActivity : AppCompatActivity() {

    // layout 바인딩 변수 선언
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // layoutInflater로 바인딩함
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색 기록 화면에서 검색어 다시 눌렀을 때 받아오는 함수
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // 받아온 결과 파싱
                val keywords = result.data?.getStringExtra("data")

                if (keywords != null) {
                    // 결과를 제대로 받아왔으면 키워드 다시 검색함
                    apiGetNaverMovieSearchData(keywords)
                    // 에디트텍스트에 검색어 입력
                    binding.etSearchMovie.setText(keywords)
                }
            }
        }

        // 검색 버튼 눌렀을 때 이벤트
        binding.btnSearchMovie.setOnClickListener {
            // 영화 정보 받아오기
            apiGetNaverMovieSearchData(binding.etSearchMovie.text.toString())
            // 영화 검색 키워드 저장
            apiInsertHistoryKeywords(binding.etSearchMovie.text.toString())

            // 검색 완료됐으면 키보드 내림
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etSearchMovie.windowToken, 0)
        }

        // 검색기록 눌렀을 때 이벤트
        binding.btnSearchHistory.setOnClickListener {
            // 검색기록 버튼 누르면 검색기록 화면으로 넘어감
            val myIntent = Intent(applicationContext, MovieHistoryKeywordsActivity::class.java)
            resultLauncher.launch(myIntent)
        }
    }

    private fun apiGetNaverMovieSearchData(keywords: String) {
        // 뷰모델 변수 선언
        val vm = MovieListViewModel(application)
        // 검색 결과 받아옴
        vm.apiGetNaverMovieSearchData(keywords, binding)
    }

    private fun apiInsertHistoryKeywords(keywords: String) {
        // 뷰모델 변수 선언
        val vm = MovieHistoryKeywordsViewModel(application)
        // 검색 기록 입력
        vm.apiInsertHistoryKeywords(keywords)
    }
}