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

import com.activeandroid.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rowland.movies.rest.enums.EBaseURlTypes;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.rowland.movies.rest.services.IRetrofitAPI;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class ApplicationController extends Application {

    /**
     * I should only ever call retrofit.create() once and re-use the
     * same instance of IMoviesAPIService every time you need to interaction with it.
     * <p/>
     * I used the regular singleton pattern in order to ensure that there only is ever a single
     * instance of this class that I use everywhere. A dependency injection framework would
     * also be something that I could used to manage these instances but would be a bit overkill since
     * I am not already utilizing it.
     */

    // The class Log identifier
    private static final String LOG_TAG = ApplicationController.class.getSimpleName();

    // Declare ApplicationController singleton instance
    private static ApplicationController instance;

    // Returns the single Application instance
    public static synchronized ApplicationController getApplicationInstance() {
        return instance;
    }

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
    }
    // Needed for factory pattern we’ll implement later in our singleton
    // Returns the single Retrofit instance
    public static Retrofit getRetrofit() {
        // Set custom date format for Gson
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // Our client
        OkHttpClient client = new OkHttpClient();
        // Set other interceptors
        client.networkInterceptors().add(new StethoInterceptor());
        // Set HttpLoggingInterceptor instance as last interceptor
        client.interceptors().add(logging);  // <-- this is the important line!
        //To send out network requests to an API_MOVIE_URL, we need to use the Retrofit builder class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EBaseURlTypes.MOVIE_API_BASE_URL.getUrlType()).addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        // Wollah! Retrofit instance is served hot.
        return retrofit;
    }
    // Factory method will return to us the appropriate IMovieService
    public IMoviesAPIService getMovieServiceOfApiType(EAPITypes apiType) {
        // with appriopriate apiType, get the IRetrofitApi with correct Endpoint
        // Wollah! Awesome encapsualtion.
        IRetrofitAPI moviesAPI = apiType.getApiType();
        // Get the MoviesAPIService and use it to retrieve a list of movies
        IMoviesAPIService movieService = moviesAPI.getMoviesApiServiceInstance();
        // Return correct movie service
        return movieService;
    }

}
