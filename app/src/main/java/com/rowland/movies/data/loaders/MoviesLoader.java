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
import com.rowland.movies.data.repository.MovieRepository;
import com.rowland.movies.rest.collections.MovieCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class MoviesLoader extends BaseLoader implements ILoader<Movie> {

    // The class Log identifier
    private static final String LOG_TAG = MoviesLoader.class.getSimpleName();
    // MovieRepository instance
    private MovieRepository mMovieRepository;
    // The sort order type
    private ESortOrder mSortOrder;
    // The list of movies our loader returns
    private List<Movie> moviesList;

    public MoviesLoader(Context context, ESortOrder mSortOrder) {
        super(context);
        this.mSortOrder = mSortOrder;
        this.mMovieRepository = new MovieRepository();
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this,new IntentFilter("MOVIES_RELOADER_DATA")));
    }

    @Override
    public List<Movie> loadInBackground() {
        // If we are online query movies from API
        if(getIsOnline()){
            // Get the MoviesAPIService and use it to retrieve a list of movies
            IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
            // Get online movies and then update local database
            getOnlineData(movieService);
            // Return the list of movies from local database
            return getLocalData();
        }
            // Return the list of movies from local database
            return getLocalData();
    }
    // Get the list of movies from online
    @Override
    public void getOnlineData(IMoviesAPIService movieService) {
        // Check wether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Online data loaded ");
        }
        // Retrieve the movies data
        Call<MovieCollection> createdCall = movieService.loadMoviesData(mSortOrder.getSortOrder(), BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new MoviesCallBack(getContext(), mSortOrder));
    }
    // Get the list of movies from local
    @Override
    public List<Movie> getLocalData() {
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Local data loaded ");
        }
        // Return local list
        return mMovieRepository.getAllWhere(mSortOrder);
    }
}