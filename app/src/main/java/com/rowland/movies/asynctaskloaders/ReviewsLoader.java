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
import android.content.IntentFilter;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.asynctaskloaders.broadcastrecievers.LoaderBroadCastReceiver;
import com.rowland.movies.asynctaskloaders.callbacks.ReviewsCallBack;
import com.rowland.movies.rest.collections.ReviewsCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.models.Reviews;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ReviewsLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = ReviewsLoader.class.getSimpleName();
    // The movie id whose reviews are retrieved
    private int mTmdbMovieId;
    // The list of movies our loader returns
    private List<Reviews> reviews;

    public ReviewsLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
        setLoaderObserver(new LoaderBroadCastReceiver(this,new IntentFilter("REVIEWS_RELOADER_DATA")));
    }

    @Override
    public List<Reviews> loadInBackground() {
        // Get the MoviesAPIService and use it to retrieve a list of reviews
        IMoviesAPIService movieService = ApplicationController.getApplicationInstance().getMovieServiceOfApiType(EAPITypes.MOVIES_API);
        // Return the list of reviews
        return getReviews(movieService);
    }
    // Get the list of reviews
    private List<Reviews> getReviews(IMoviesAPIService movieService) {
        // Retrieve the reviews data
        Call<ReviewsCollection> createdCall = movieService.loadReviewsData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new ReviewsCallBack(getContext()){
            // Gain access to the Reviews List
            @Override
            public void retrieveReviewsList() {
                reviews = super.getReviewsList();
            }
        });

        if (reviews.size() != 0) {
            return reviews;
        }

        return null;

    }
}
