package com.rowland.movies.rest.services;

import com.rowland.movies.rest.data.MoviesData;
import com.rowland.movies.rest.data.ReviewsData;
import com.rowland.movies.rest.data.TrailersData;
import com.rowland.movies.rest.pojos.Movies;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Rowland  on 12/11/2015.
 */
public interface IMDbEndPointsApi {

    /**
     * With Retrofit 2, endpoints are defined inside of an interface using special retrofit annotations
     * to encode details about the parameters and request method.
     *
     * Created by Rowland  on 12/11/2015.
     * */

    // Will load our movies
    @GET("/3/discover/movie")
    Call<Movies> loadMoviesData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailer videos
    @GET("/3/movie/{id}/videos")
    Call<TrailersData> loadTrailersData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailers
    @GET("/3/movie/{id}/reviews")
    Call<ReviewsData> loadReviewsData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);
}
