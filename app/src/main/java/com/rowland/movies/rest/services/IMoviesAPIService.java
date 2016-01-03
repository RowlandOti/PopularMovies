/*
 * Copyright 2015 Oti Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.rowland.movies.rest.services;

import com.rowland.movies.rest.collections.MovieCollection;
import com.rowland.movies.rest.collections.ReviewCollection;
import com.rowland.movies.rest.collections.TrailerCollection;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Oti Rowland  on 12/11/2015.
 */
public interface IMoviesAPIService {

    /**
     * With Retrofit 2, endpoints are defined inside of an interface using special retrofit annotations
     * to encode details about the parameters and request method.
     **/

    // Define all our endpoints
    String MOVIE_ENDPOINT = "/3/discover/movie";
    String MOVIE_TRAILERS_ENDPOINT = "/3/movie/{id}/videos";
    String MOVIE_REVIEWS_ENDPOINT = "/3/movie/{id}/reviews";

    // Load our movies
    @GET(MOVIE_ENDPOINT)
    Call<MovieCollection> loadMovieData(@Query("sort_by") String sortBy, @Query("page") int pageNo, @Query("api_key") String apiKey);

    // Load the movie's trailer videos
    @GET(MOVIE_TRAILERS_ENDPOINT)
    Call<TrailerCollection> loadTrailerData(@Path("id") long mTmdbMovieId, @Query("page") int pageNo, @Query("api_key") String apiKey);

    // Load the movie's trailers
    @GET(MOVIE_REVIEWS_ENDPOINT)
    Call<ReviewCollection> loadReviewData(@Path("id") long mTmdbMovieId, @Query("page") int pageNo, @Query("api_key") String apiKey);
}
