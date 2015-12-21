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
import android.text.TextUtils;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.asynctaskloaders.callbacks.TrailersCallBack;
import com.rowland.movies.rest.collections.TrailersCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.models.Trailers;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.rowland.movies.rest.services.IRetrofitAPI;

import java.util.List;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailersLoader extends BaseLoader {
    // The class Log identifier
    private static final String LOG_TAG = TrailersLoader.class.getSimpleName();
    // The movie id whose trailers are retrieved
    private int mTmdbMovieId;
    // The list of trailers our loader returns
    private List<Trailers> trailers;

    public TrailersLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
    }

    @Override
    public List<Trailers> loadInBackground() {
        // Get the RetrofitApi with correct Endpoint
        IRetrofitAPI moviesAPI = ApplicationController.getApplicationInstance().getApiOfType(EAPITypes.MOVIES_API);
        // Get the MoviesAPIService
        IMoviesAPIService movieService = moviesAPI.getMoviesApiServiceInstance();
        // Return the list of trailers
        return getTrailers(movieService);
    }
    // Get the list of reviews
    private List<Trailers> getTrailers(IMoviesAPIService movieService) {
        // Retrieve the reviews data
        Call<TrailersCollection> createdCall = movieService.loadTrailersData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);
        // Asynchronous access
        createdCall.enqueue(new TrailersCallBack(){

            @Override
            public void retrieveTrailersList() {
                trailers = super.getTrailersList();
            }
        });

        if (trailers.size() != 0) {
            return trailers;
        }

        return null;

    }

    // Extract the individual movie trailers
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
