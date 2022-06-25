package com.flow.moviesearchnative

data class MovieHistoryKeywordsResult(var result: String)
data class MovieHistoryKeywordsItem(val id: Int, val keywords: String, val createDateTime: String)