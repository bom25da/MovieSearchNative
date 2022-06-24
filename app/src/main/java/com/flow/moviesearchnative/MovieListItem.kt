package com.flow.moviesearchnative

class MovieList (var items: MutableList<MovieListItem>)
class MovieListItem (val image: String, val title: String, val pubDate: String, val userRating: String, val link: String)