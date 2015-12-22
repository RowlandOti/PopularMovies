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
import com.rowland.movies.rest.collections.MoviesCollection;
import com.rowland.movies.rest.models.Movies;
import com.rowland.movies.rest.models.RestError;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/21/2015.
 */
public class MoviesCallBack implements Callback<MoviesCollection> {

    // The class Log identifier
    private static final String LOG_TAG = MoviesCallBack.class.getSimpleName();
    // The list of movies our loader returns
    private List<Movies> moviesList;
    // Context instance
    private Context context;

    public MoviesCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Response<MoviesCollection> response, Retrofit retrofit) {

        if (response.isSuccess() && response.errorBody() == null) {
            // movies available
            moviesList = response.body().getResults();

            for (Movies movie : moviesList) {
                // Save movies in the database
                movie.save();
                // Check wether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Movie " + movie.getTitle());
                    Log.d(LOG_TAG, "Movie " + movie.getReleaseDate());
                }
                // BroadCast the changes locally
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("MOVIES_RELOADER_DATA"));
            }
        } else {

            try {
                RestError restError = (RestError) retrofit
                        .responseConverter(RestError.class, RestError.class.getAnnotations())
                        .convert(response.errorBody());
                if (BuildConfig.IS_DEBUG_MODE) {
                    // we got an error message - Do error handling here
                    Log.d(LOG_TAG, restError.getStrMesage());
                    //For getting error code. Code is integer value like 200,404 etc
                    Log.d(LOG_TAG, String.valueOf(restError.getCode()));
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
