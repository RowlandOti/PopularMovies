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
import com.rowland.movies.data.callbacks.TrailersCallBack;
import com.rowland.movies.data.interfaces.ILoader;
import com.rowland.movies.rest.collections.TrailersCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.models.Trailers;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailersLoader extends BaseLoader implements ILoader<Trailers> {
    // The class Log identifier
    private static final String LOG_TAG = TrailersLoader.class.getSimpleName();
    // The movie id whose trailersList are retrieved
    private int mTmdbMovieId;
    // The list of trailersList our loader returns
    private List<Trailers> trailersList;
    // Check if we are online
    private boolean isOnline;

    public TrailersLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
        // Set the data set change observer
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this,new IntentFilter("TRAILERS_RELOADER_DATA")));
        // Set the network change observer
        setNetworkChangeObserver(new NetworkChangeBroadCastReceiver(this));
    }

    @Override
    public List<Trailers> loadInBackground() {
        // If we are online query movies from API
        if(getIsOnline()) {
            // Get the MoviesAPIService and use it to retrieve a list of trailersList
            IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
            // Return the list of trailersList
            return getOnlineData(movieService);
        }
        // Return the list of movies from local
        return getLocalData();
    }
    // Get the list of reviews
    @Override
    public List<Trailers> getOnlineData(IMoviesAPIService movieService) {
        // Retrieve the reviews data
        Call<TrailersCollection> createdCall = movieService.loadTrailersData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new TrailersCallBack(getContext()){
            // Gain access to the TrailersList
            @Override
            public void retrieveTrailersList() {
                trailersList = super.getTrailersList();
            }
        });

        if (trailersList.size() != 0) {
            return trailersList;
        }
        // Return null, if there are no trailersList
        return null;

    }
    // Get the list of reviews from local
    @Override
    public List<Trailers> getLocalData() {
        // Return local list
        return trailersList;
    }
    // Extract the individual movie trailersList
    // Handy method, might help in future
    private Trailers extractTrailer(TrailersCollection videos) {
        // If no trailer videos are found return
        if (videos == null || videos.results == null || videos.results.size() == 0) {
            return null;
        }
        // Pop out the lead YouTube trailer
        for (Trailers video : videos.results) {
            if ("Trailer".equals(video.getType()) && "YouTube".equals(video.getSite()) && !TextUtils.isEmpty(video.getKey())) {
                return video;
            }
        }
        return null;

    }
}
