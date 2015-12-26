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

package com.rowland.movies.data.callbacks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.data.loaders.MovieLoader;
import com.rowland.movies.data.repository.MovieRepository;
import com.rowland.movies.rest.collections.MovieCollection;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.RestError;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/21/2015.
 */
public class MovieCallBack implements Callback<MovieCollection> {

    // The class Log identifier
    private static final String LOG_TAG = MovieCallBack.class.getSimpleName();
    // Context instance
    private Context context;
    // Our sort order
    private ESortOrder mSortOrder;

    public MovieCallBack(Context context, ESortOrder sortOrder) {
        this.mSortOrder = sortOrder;
        this.context = context;
    }

    @Override
    public void onResponse(Response<MovieCollection> response, Retrofit retrofit) {
        // Check status of response before proceeding
        if (response.isSuccess() && response.errorBody() == null) {
            // movies available
            List<Movie> moviesList = response.body().getResults();
            // MovieRepository instance
            MovieRepository mMovieRepository = new MovieRepository();
            // Save movies to data storage
            mMovieRepository.saveAll(moviesList, mSortOrder);
            // BroadCast the changes locally
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(MovieLoader.INTENT_ACTION));
        } else {

            try {
                RestError restError = (RestError) retrofit
                        .responseConverter(RestError.class, RestError.class.getAnnotations())
                        .convert(response.errorBody());
                if (BuildConfig.IS_DEBUG_MODE) {
                    // we got an error message - Do error handling here
                    //Log.d(LOG_TAG, restError.getErrorMesage());
                    Log.d(LOG_TAG, response.errorBody().toString());

                    //For getting error code. Code is integer value like 200,404 etc
                    //Log.d(LOG_TAG, String.valueOf(restError.getCode()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        // Inform user of failure due to no network e.t.c
        Log.d(LOG_TAG, t.getMessage());
    }
}
