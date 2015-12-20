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

package com.rowland.movies.rest.callbacks;

import android.util.Log;

import com.rowland.movies.rest.pojos.RestError;

import java.io.IOException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/17/2015.
 */
public abstract class RestCallBack<ReviewsData> implements Callback<ReviewsData> {

    @Override
    public void onResponse(Response<ReviewsData> response, Retrofit retrofit) {

        if (response.isSuccess() && response.errorBody() == null) {
            // tasks available
            response.body();
            //List<Reviews> items = response.body().items;
        } else {

            try {
                RestError restError = (RestError) retrofit.responseConverter(RestError.class,
                        RestError.class.getAnnotations())
                        .convert(response.errorBody());
                // Do error handling here
                // we got an error message
                Log.d("Error message", restError.getStrMesage());
                //For getting error code. Code is integer value like 200,404 etc
                Log.d("Error code", String.valueOf(restError.getCode()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        // something went horribly wrong (like no internet connection)
        Log.d("Error", t.getMessage());
    }
}
