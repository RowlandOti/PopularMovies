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

package com.rowland.movies.asynctaskloaders.callbacks;

import android.util.Log;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.rest.collections.ReviewsCollection;
import com.rowland.movies.rest.pojos.RestError;
import com.rowland.movies.rest.pojos.Reviews;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/21/2015.
 */
public abstract class ReviewsCallBack implements Callback<ReviewsCollection> {

    // The class Log identifier
    private static final String LOG_TAG = MoviesCallBack.class.getSimpleName();
    // The list of reviews our loader returns
    private List<Reviews> reviewsList;

    @Override
    public void onResponse(Response<ReviewsCollection> response, Retrofit retrofit) {

        if (response.isSuccess() && response.errorBody() == null) {
            // movies available
            reviewsList = response.body().getResults();

            for (Reviews review : reviewsList) {
                // Save reviews in the database
                review.save();
                // Check wether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Review " + review.getAuthor());
                    Log.d(LOG_TAG, "Review " + review.getContent());
                }
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
    // Getter method for moviesCollection
    public List<Reviews> getReviewsList() {

        return this.reviewsList;
    }
    // A handy method to retrieve the collection from the callback
    // Implement this method to gain access
    public abstract void retrieveReviewsList();
}
