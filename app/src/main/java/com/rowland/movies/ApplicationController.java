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

package com.rowland.movies;

import android.app.Application;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ApplicationController extends Application {


    // Specify the base URL for the API to request.
    static final String API_MOVIE_URL = "http://api.themoviedb.org";
    // Declare ApplicationController singleton instance
    private static ApplicationController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized ApplicationController getInstance() {
        return instance;
    }

    public static Retrofit getRetrofit() {
        //To send out network requests to an API, we need to use the Retrofit builder class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_MOVIE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        // Wollah! this instance exists through out the appication lifecycle.
        return retrofit;
    }
}
