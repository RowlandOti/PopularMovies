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
import android.util.Log;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.broadcastrecievers.DataSetChangeBroadCastReceiver;
import com.rowland.movies.data.repository.TrailerRepository;
import com.rowland.movies.rest.collections.TrailerCollection;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Trailer;

import java.util.List;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailerLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = TrailerLoader.class.getSimpleName();
    // The movie id whose trailersList are retrieved
    private Movie mMovie;

    public TrailerLoader(Context context, Movie movie) {
        super(context);
        this.mMovie = movie;
        // Set the data set change observer
        setDataSetChangeObserver(new DataSetChangeBroadCastReceiver(this, new IntentFilter("TRAILERS_RELOADER_DATA")));
    }

    @Override
    public List<Trailer> loadInBackground() {
        // Return the list of movies from local database
        return getLocalData();
    }

    // Get the list of movies from local
    @Override
    public List<Trailer> getLocalData() {
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Local data loaded ");
        }
        // Movie repository in use
        TrailerRepository mTrailerRepository = new TrailerRepository();
        // Return local list
        return mTrailerRepository.getAllWhere(mMovie);
    }

    // Extract the individual trailers from List
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