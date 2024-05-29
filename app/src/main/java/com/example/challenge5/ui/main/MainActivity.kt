package com.example.challenge5.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge5.R
import com.example.challenge5.data.model.Movie
import com.example.challenge5.data.remote.MovieApiService
import com.example.challenge5.ui.details.MovieDetailActivity
import com.example.challenge5.ui.profile.ProfileActivity
import com.example.challenge5.ui.main.MovieAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var movieApiService: MovieApiService
    private val apiKey = "YOUR_API_KEY_HERE"
    private lateinit var movieAdapter: MovieAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.homeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter { movie -> showMovieDetails(movie) }
        recyclerView.adapter = movieAdapter

        findViewById<ImageView>(R.id.profileIcon).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        setupRetrofit()
        fetchPopularMovies()
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApiService = retrofit.create(MovieApiService::class.java)
    }

    private fun fetchPopularMovies() {
        GlobalScope.launch {
            val movieResponse = movieApiService.getPopularMovies(apiKey)
            runOnUiThread {
                movieAdapter.submitList(movieResponse.results)
            }
        }
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }
}
