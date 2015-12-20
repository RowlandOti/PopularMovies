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
 */

package com.rowland.movies.asynctaskloaders;

import android.content.Context;
import android.util.Log;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.enums.ESortOrder;
import com.rowland.movies.rest.collections.MoviesCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.pojos.Movies;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.rowland.movies.rest.services.IRetrofitAPI;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class MoviesLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = MoviesLoader.class.getSimpleName();
    // The sort order type
    private ESortOrder mSortOrder;
    // The list of movies our loader returns
    private List<Movies> movies;

    public MoviesLoader(Context context, ESortOrder mSortOrder) {
        super(context);
        this.mSortOrder = mSortOrder;
    }

    @Override
    public List<Movies> loadInBackground() {
        // Get the RetrofitApi with correct Endpoint
        IRetrofitAPI moviesAPI = ApplicationController.getApplicationInstance().getApiOfType(EAPITypes.MOVIES_API);
        // Get the MoviesAPIService and use it to retrieve a list of movies
        IMoviesAPIService movieService = moviesAPI.getMoviesApiServiceInstance();
        // Return the list of movies
        return getMovies(movieService);
    }

    private List<Movies> getMovies(IMoviesAPIService movieService) {
        // Retrieve the movies data
        Call<MoviesCollection> createdCall = movieService.loadMoviesData(mSortOrder.getSortOrder(), BuildConfig.IMDB_API_KEY);
        // Asynchronously access
        createdCall.enqueue(new Callback<MoviesCollection>() {
            @Override
            public void onResponse(Response<MoviesCollection> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    // movies available
                    movies = response.body().results;

                    for (Movies movie : movies) {
                        // Save movies in the database
                        movie.save();
                        // Check wether we are in debug mode
                        if(BuildConfig.IS_DEBUG_MODE) {
                            Log.d(LOG_TAG, "Movie " + movie.getTitle());
                        }
                    }
                } else {
                    // error response, no access to resource?
                    Log.d(LOG_TAG, "No accessible resources found");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Inform user of failure due to no network e.t.c
                Log.d(LOG_TAG, t.getMessage());
            }
        });

        if (movies != null && !movies.isEmpty()) {
            return movies;
        }

        return null;
    }
}