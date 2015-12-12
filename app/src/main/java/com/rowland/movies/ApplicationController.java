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

import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.services.IRetrofitAPI;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ApplicationController extends Application {

    // Declare ApplicationController singleton instance
    private static ApplicationController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    // Returns the single Application instance
    public static synchronized ApplicationController getApplicationInstance() {
        return instance;
    }
    // Needed for factory pattern we’ll implement later in our singleton
    // Returns the single Retrofit instance
    public static Retrofit getRetrofit() {
        //To send out network requests to an API_MOVIE_URL, we need to use the Retrofit builder class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_MOVIE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        // Wollah! Retrofit instance is served hot.
        return retrofit;
    }
    // Factory method will return to us the appropriate IRetrofitAPI Whenever we need to access our api
    // we call ApplicationController.getApiOfType method with appriopriate type and we get class with
    // desired enpoints. Wollah! Awesome encapsualtion.
    public IRetrofitAPI getApiOfType(EAPITypes type) {

        return type.getApiType();
    }

}
