package com.example.challenge5.ui.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.challenge5.R
import com.example.challenge5.data.model.Movie
import com.example.challenge5.data.remote.MovieApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieApiService: MovieApiService
    private val apiKey = "YOUR_API_KEY_HERE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        setupRetrofit()

        GlobalScope.launch {
            val movie = movieApiService.getMovieById(movieId, apiKey)
            runOnUiThread {
                displayMovieDetails(movie)
            }
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApiService = retrofit.create(MovieApiService::class.java)
    }

    private fun displayMovieDetails(movie: Movie) {
        findViewById<TextView>(R.id.movieTitleTextView).text = movie.title
        findViewById<TextView>(R.id.movieOverviewTextView).text = movie.overview
        findViewById<TextView>(R.id.movieReleaseDateTextView).text = movie.releaseDate

        val posterImageView = findViewById<ImageView>(R.id.moviePosterImageView)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(posterImageView)
    }
}
