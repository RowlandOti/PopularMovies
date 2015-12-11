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

package com.rowland.movies.rest.services;

import android.content.Context;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/11/2015.
 */
public class IMDbEndpointsApiSingleton {

    public static final String API_MOVIE_URL = "http://api.themoviedb.org";
    // The singleton instance returned
    private static IMDbEndPointsApi imDbEndPointsApiInstance;

    public static IMDbEndPointsApi getInstance()
    {
        return imDbEndPointsApiInstance;
    }

    private IMDbEndpointsApiSingleton()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_MOVIE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        imDbEndPointsApiInstance = retrofit.create(IMDbEndPointsApi.class);
    }
}
