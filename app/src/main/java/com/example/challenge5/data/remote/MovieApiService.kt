package com.example.challenge5.data.remote

import com.example.challenge5.data.model.Movie
import com.example.challenge5.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") id: Int, @Query("api_key") apiKey: String): Movie

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse
}
