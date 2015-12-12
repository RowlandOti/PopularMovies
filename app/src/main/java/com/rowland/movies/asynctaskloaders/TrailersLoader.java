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

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.rest.data.MoviesData;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.pojos.Movies;
import com.rowland.movies.rest.services.IRetrofitAPI;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.uwetrottmann.androidutils.GenericSimpleLoader;

import retrofit.Call;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailersLoader extends GenericSimpleLoader<Movies> {

    public TrailersLoader(Context context) {
        super(context);
    }

    @Override
    public Movies loadInBackground() {
        Call<MoviesData> createdCall = HttpUtil.getService().getMovieResults(BuildConfig.MOVIE_API_KEY,Constants.SORT_BY_POPULARITY_DESC));
        IRetrofitAPI moviesAPI = ApplicationController.getApplicationInstance().getApiOfType(EAPITypes.MOVIES_API);
        IMoviesAPIService movieService = moviesAPI.getMoviesApiServiceInstance();;
        return null;
    }

}
