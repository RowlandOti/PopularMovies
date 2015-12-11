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

import com.rowland.movies.rest.data.MoviesData;
import com.rowland.movies.rest.data.ReviewsData;
import com.rowland.movies.rest.data.TrailersData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Rowland  on 12/11/2015.
 */
public interface IMDbEndPointsApiService {

    /**
     * With Retrofit 2, endpoints are defined inside of an interface using special retrofit annotations
     * to encode details about the parameters and request method.
     **/

    // Will load our movies
    @GET("/3/discover/movie")
    Call<MoviesData> loadMoviesData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailer videos
    @GET("/3/movie/{id}/videos")
    Call<TrailersData> loadTrailersData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    // Will load the movie's trailers
    @GET("/3/movie/{id}/reviews")
    Call<ReviewsData> loadReviewsData(@Query("apiKey") String apiKey, @Query("sortBy") String sortBy);
}
