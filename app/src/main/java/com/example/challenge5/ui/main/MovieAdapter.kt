package com.example.challenge5.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge5.R
import com.example.challenge5.data.model.Movie

class MovieAdapter(private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    fun submitList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View, private val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            movieTitleTextView.text = movie.title
            itemView.setOnClickListener { onClick(movie) }
        }
    }
}
