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

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.asynctaskloaders.callbacks.MoviesCallBack;
import com.rowland.movies.enums.ESortOrder;
import com.rowland.movies.rest.collections.MoviesCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.pojos.Movies;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.rowland.movies.rest.services.IRetrofitAPI;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class MoviesLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = MoviesLoader.class.getSimpleName();
    // The sort order type
    private ESortOrder mSortOrder;
    // The list of movies our loader returns
    private List<Movies> moviesList;

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
    // Get the list of movies
    private List<Movies> getMovies(IMoviesAPIService movieService) {
        // Retrieve the movies data
        Call<MoviesCollection> createdCall = movieService.loadMoviesData(mSortOrder.getSortOrder(), BuildConfig.IMDB_API_KEY);
        // Asynchronously access
        createdCall.enqueue(new MoviesCallBack() {
            // Gain access to the MoviesList
            @Override
            public void retrieveMoviesList() {
                moviesList = super.getMoviesList();
            }
        });
        // Return the list of movies
        if (moviesList != null && !moviesList.isEmpty()) {
            return moviesList;
        }

        return null;
    }
}