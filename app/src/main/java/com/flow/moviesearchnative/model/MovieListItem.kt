package com.flow.moviesearchnative

data class MovieList (var items: MutableList<MovieListItem>)
data class MovieListItem (val image: String, val title: String, val pubDate: String, val userRating: String, val link: String)