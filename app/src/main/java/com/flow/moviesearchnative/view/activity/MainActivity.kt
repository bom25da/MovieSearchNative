package com.flow.moviesearchnative.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.flow.moviesearchnative.databinding.ActivityMainBinding
import com.flow.moviesearchnative.viewModel.MovieHistoryKeywordsViewModel
import com.flow.moviesearchnative.viewModel.MovieListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val keywords = result.data?.getStringExtra("data")

                if (keywords != null) {
                    apiGetNaverMovieSearchData(keywords)
                    binding.etSearchMovie.setText(keywords)
                }
            }
        }

        // 검색 버튼 눌렀을 때 이벤트
        binding.btnSearchMovie.setOnClickListener {
            apiGetNaverMovieSearchData(binding.etSearchMovie.text.toString())
            apiInsertHistoryKeywords(binding.etSearchMovie.text.toString())
        }

        // 검색기록 눌렀을 때 이벤트
        binding.btnSearchHistory.setOnClickListener {
            val myIntent = Intent(applicationContext, MovieHistoryKeywordsActivity::class.java)
            resultLauncher.launch(myIntent)
        }
    }

    private fun apiGetNaverMovieSearchData(keywords: String) {
        val vm = MovieListViewModel(application)
        vm.apiGetNaverMovieSearchData(keywords, binding)
    }

    private fun apiInsertHistoryKeywords(keywords: String) {
        val vm = MovieHistoryKeywordsViewModel(application)
        vm.apiInsertHistoryKeywords(keywords)
    }
}