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
import android.text.TextUtils;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.broadcastrecievers.DataSetChangeBroadCastReceiver;
import com.rowland.movies.data.broadcastrecievers.NetworkChangeBroadCastReceiver;
import com.rowland.movies.data.callbacks.TrailerCallBack;
import com.rowland.movies.data.interfaces.ILoader;
import com.rowland.movies.rest.collections.TrailerCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.models.Trailer;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailerLoader extends BaseLoader implements ILoader<Trailer> {
    // The class Log identifier
    private static final String LOG_TAG = TrailerLoader.class.getSimpleName();
    // The movie id whose trailersList are retrieved
    private int mTmdbMovieId;
    // The list of trailersList our loader returns
    private List<Trailer> trailersList;
    // Check if we are online
    private boolean isOnline;

    public TrailerLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
        // Set the data set change observer
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this,new IntentFilter("TRAILERS_RELOADER_DATA")));
        // Set the network change observer
        setNetworkChangeObserver(new NetworkChangeBroadCastReceiver(this));
    }

    @Override
    public List<Trailer> loadInBackground() {
        // If we are online query movies from API
        if(getIsOnline()) {
            // Get the MoviesAPIService and use it to retrieve a list of trailersList
            IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
            // Get online trailers and then update local database
            getOnlineData(movieService);
            // Return the list of trailers from local database
            return getLocalData();
        }
        // Return the list of movies from local database
        return getLocalData();

    }
    // Get the list of reviews
    @Override
    public void getOnlineData(IMoviesAPIService movieService) {
        // Retrieve the reviews data
        Call<TrailerCollection> createdCall = movieService.loadTrailersData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new TrailerCallBack(getContext()){
            // Gain access to the TrailersList
            @Override
            public void retrieveTrailersList() {
                trailersList = super.getTrailersList();
            }
        });

    }
    // Get the list of reviews from local
    @Override
    public List<Trailer> getLocalData() {
        // Return local list
        return trailersList;
    }
    // Extract the individual movie trailersList
    // Handy method, might help in future
    private Trailer extractTrailer(TrailerCollection videos) {
        // If no trailer videos are found return
        if (videos == null || videos.results == null || videos.results.size() == 0) {
            return null;
        }
        // Pop out the lead YouTube trailer
        for (Trailer video : videos.results) {
            if ("Trailer".equals(video.getType()) && "YouTube".equals(video.getSite()) && !TextUtils.isEmpty(video.getKey())) {
                return video;
            }
        }
        return null;

    }
}
