/*
    Category    : Model
    Title       : 영화 키워드 검색 결과 객체
    Description : api로 전송 받는 객체를 담는 용도
*/

package com.flow.moviesearchnative

data class MovieList (var items: MutableList<MovieListItem>)
data class MovieListItem (val image: String, val title: String, val pubDate: String, val userRating: String, val link: String)