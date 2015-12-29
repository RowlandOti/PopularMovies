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

import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.broadcastrecievers.DataSetChangeBroadCastReceiver;
import com.rowland.movies.data.repository.ReviewRepository;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Review;

import java.util.List;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ReviewLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = ReviewLoader.class.getSimpleName();
    // The movie id whose reviewsList are retrieved
    private Movie mMovie;
    // The list of movies our loader returns
    private List<Review> reviewsList;

    public ReviewLoader(Context context, Movie movie) {
        super(context);
        this.mMovie = movie;
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this, new IntentFilter("REVIEWS_RELOADER_DATA")));
    }

    @Override
    public List<Review> loadInBackground() {
        // Return the list of movies from local database
        return getLocalData();
    }

    // Get the list of movies from local
    @Override
    public List<Review> getLocalData() {
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Local data loaded ");
        }
        // Movie repository in use
        ReviewRepository mReviewRepository = new ReviewRepository();
        // Return local list
        return mReviewRepository.getAllWhere(mMovie);
    }
}