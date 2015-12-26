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

package com.rowland.movies.rest.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.callbacks.MovieCallBack;
import com.rowland.movies.data.repository.MovieRepository;
import com.rowland.movies.rest.collections.MovieCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.enums.ESortOrder;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/25/2015.
 */
public class MovieIntentService extends IntentService {
    // The class Log identifier
    private static final String LOG_TAG = MovieIntentService.class.getSimpleName();
    // The string identifier
    public static final String REQUEST_STRING = "SORT_TYPE";
    // The request string value
    private String requestString;
    // The sort order type
    private ESortOrder mSortOrder;

    // MovieRepository instance
    private MovieRepository mMovieRepository;

    // Default Constructor
    public MovieIntentService() {
        // Name worker thread
        super("movie-intent-service");
    }

    @Override
    public void onCreate() {
        // Overriding onCreate() requires a call to super().
        super.onCreate();
    }

    // What will happen when service is triggered
    @Override
    protected void onHandleIntent(Intent intent) {
        //Acquire the sort type string
        requestString = intent.getStringExtra(REQUEST_STRING);
        // Set the sort type to use
        setSortType(requestString);
        // Set the Movie Repository
        setMovieRepository(new MovieRepository());
        // Go get some online data
        getOnlineData();
    }

    // Get the list of movies from online
    private void getOnlineData() {
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Online data loaded ");
        }
        // Get the MoviesAPIService and use it to retrieve a list of movies
        IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
        // Retrieve the movies data
        Call<MovieCollection> createdCall = movieService.loadMoviesData(requestString, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new MovieCallBack(mMovieRepository, mSortOrder));
    }

    private void setSortType(String requestString) {
        // Set the sort type to use
        switch (requestString) {
            case "popularity.desc":
                mSortOrder = ESortOrder.POPULAR_DESCENDING;
                break;
            case "vote_average.desc":
                mSortOrder = ESortOrder.HIGHEST_RATED_DESCENDING;
                break;
            case "favourite.desc":
                mSortOrder = ESortOrder.FAVOURITE_DESCENDING;
                break;
        }
    }
    // Set the MovieRepository
    public void setMovieRepository(MovieRepository movieRepository) {
        this.mMovieRepository = movieRepository;
    }

}
