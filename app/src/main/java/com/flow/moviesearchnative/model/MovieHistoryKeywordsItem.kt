/*
    Category    : Model
    Title       : 영화 키워드 검색 기록 객체
    Description : api로 전송 받는 객체를 담는 용도
*/

package com.flow.moviesearchnative

data class MovieHistoryKeywordsResult(var result: String)
data class MovieHistoryKeywordsItem(val id: Int, val keywords: String, val createDateTime: String)