package com.rowland.rest.data;

import com.rowland.objects.MovieReviews;
import com.rowland.objects.MovieTrailers;
import com.rowland.objects.Movies;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Rowland  on 12/11/2015.
 */
public interface IMDbEndPointsApi {

    // Will load our movies
    @GET("/3/discover/movie")
    Call<Movies> loadMovies(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailer videos
    @GET("/3/movie/{id}/videos")
    Call<TrailersData> loadMovieTrailers(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailers
    @GET("/3/movie/{id}/reviews")
    Call<ReviewsData> loadMovieReviews(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);
}
