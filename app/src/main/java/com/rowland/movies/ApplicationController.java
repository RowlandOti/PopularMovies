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

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.services.IRetrofitAPI;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ApplicationController extends Application {

    /**
     * I should only ever call retrofit.create() once and re-use the
     * same instance of IMoviesAPIService every time you need to interaction with it.
     *
     * I used the regular singleton pattern in order to ensure that there only is ever a single
     * instance of this class that I use everywhere. A dependency injection framework would
     * also be something that I could used to manage these instances but would be a bit overkill since
     * I am not already utilizing it.
     */

    // Declare ApplicationController singleton instance
    private static ApplicationController instance;

    @Override
    public void onCreate() {
        /**
         *  Retrofit and OkHttp can be hard to troubleshoot when trying to step through the various layers of
         *  abstraction in the libraries. Facebook's Stetho project enables you to use Chrome to inspect all network traffic.
         *  Visit chrome://inspect on your Chrome desktop and your emulator/device should appear.
         *  Click on Inspect to launch a new window. Click on the Network tab. Now you can start watching network traffic
         *  between your emulator or device in real-time!
         */
        super.onCreate();
        instance = this;

        Stetho.initializeWithDefaults(this);
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
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
