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

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.broadcastrecievers.DataSetChangeBroadCastReceiver;
import com.rowland.movies.data.broadcastrecievers.NetworkChangeBroadCastReceiver;
import com.rowland.movies.data.callbacks.ReviewsCallBack;
import com.rowland.movies.data.interfaces.ILoader;
import com.rowland.movies.rest.collections.ReviewsCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.models.Review;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ReviewsLoader extends BaseLoader implements ILoader<Review> {
    // The class Log identifier
    private static final String LOG_TAG = ReviewsLoader.class.getSimpleName();
    // The movie id whose reviewsList are retrieved
    private int mTmdbMovieId;
    // The list of movies our loader returns
    private List<Review> reviewsList;

    public ReviewsLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this, new IntentFilter("REVIEWS_RELOADER_DATA")));
        setNetworkChangeObserver(new NetworkChangeBroadCastReceiver(this));
    }

    @Override
    public List<Review> loadInBackground() {
        // If we are online query movies from API
        if (getIsOnline()) {
            // Get the MoviesAPIService and use it to retrieve a list of reviewsList
            IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
            // Get online movies and then update local database
            getOnlineData(movieService);
            // Return the list of movies from local database
            return getLocalData();
        }
        // Return the list of movies from local database
        return getLocalData();

    }

    // Get the list of reviews from online
    @Override
    public void getOnlineData(IMoviesAPIService movieService) {
        // Retrieve the reviewsList data
        Call<ReviewsCollection> createdCall = movieService.loadReviewsData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new ReviewsCallBack(getContext()) {
            // Gain access to the Review List
            @Override
            public void retrieveReviewsList() {
                reviewsList = super.getReviewsList();
            }
        });
    }

    // Get the list of reviews from local
    @Override
    public List<Review> getLocalData() {
        // Return local list
        return reviewsList;
    }
}
