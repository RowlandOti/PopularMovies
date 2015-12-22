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

package com.rowland.movies.data.loaders;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.broadcastrecievers.DataSetChangeBroadCastReceiver;
import com.rowland.movies.data.callbacks.MoviesCallBack;
import com.rowland.movies.data.interfaces.ILoader;
import com.rowland.movies.rest.collections.MoviesCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movies;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class MoviesLoader extends BaseLoader implements ILoader<Movies> {

    // The class Log identifier
    private static final String LOG_TAG = MoviesLoader.class.getSimpleName();
    // The sort order type
    private ESortOrder mSortOrder;
    // The list of movies our loader returns
    private List<Movies> moviesList;

    public MoviesLoader(Context context, ESortOrder mSortOrder) {
        super(context);
        this.mSortOrder = mSortOrder;
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this,new IntentFilter("MOVIES_RELOADER_DATA")));
    }

    @Override
    public List<Movies> loadInBackground() {
        // If we are online query movies from API
        if(getIsOnline()){
            // Get the MoviesAPIService and use it to retrieve a list of movies
            IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
            // Return the list of movies from online
            return getOnlineData(movieService);
        }
            // Return the list of movies from local
            return getLocalData();
    }
    // Get the list of movies from online
    @Override
    public List<Movies> getOnlineData(IMoviesAPIService movieService) {
        // Check wether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Online data loaded ");
        }
        // Retrieve the movies data
        Call<MoviesCollection> createdCall = movieService.loadMoviesData(mSortOrder.getSortOrder(), BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new MoviesCallBack(getContext()) {
            // ToDo: Remove method, it never gets called
            // Gain access to the MoviesList
            @Override
            public void retrieveMoviesList() {
                moviesList = super.getMoviesList();
                // Check whether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "List Size "+moviesList.size());
                }
            }
        });

        // Return the list of online movies
        if (moviesList != null && !moviesList.isEmpty()) {
            // Return online list
            return moviesList;
        }
        // Return null, if there are no movies
        return null;
    }
    // Get the list of movies from local
    @Override
    public List<Movies> getLocalData() {
        // Check wether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Local data loaded ");
        }
        // Return local list
        return moviesList;
    }
}