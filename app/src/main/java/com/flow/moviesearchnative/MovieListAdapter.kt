package com.flow.moviesearchnative

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flow.moviesearchnative.databinding.MovieSearchListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class MovieListAdapter(private val context: Context, private val MainList: MutableList<MovieListItem>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    //클릭 인터페이스를 정의.
    interface MyItemClickListener {
        fun onItemClick(position: Int)
        //fun onLongClick(position: Int)
    }

    //클릭 리스너 선언
    private lateinit var mItemClickListener: MyItemClickListener

    //클릭 리스너 등록 메서드 ( 메인 액티비티에서 람다식 혹은 inner 클래스로 호출)
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_search_list, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = MainList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(MainList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //초기화
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
//            itemView.setOnLongClickListener {
//                mItemClickListener.onLongClick(adapterPosition)
//                return@setOnLongClickListener true
//            }
        }

        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val movieDate: TextView = itemView.findViewById(R.id.movieDate)
        private val movieRating: TextView = itemView.findViewById(R.id.movieRating)

        fun bind(item: MovieListItem) {
            Glide.with(itemView).load(item.image).into(movieImage)
            movieTitle.text = item.title
            movieDate.text = item.pubDate
            movieRating.text = item.userRating
        }

    }

}