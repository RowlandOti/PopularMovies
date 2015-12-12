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

package com.rowland.movies.enums;

import com.rowland.movies.rest.services.ARetrofitAPI;
import com.rowland.movies.rest.services.MoviesAPI;

/**
 * Created by Oti Rowland on 12/12/2015.
 */

public enum APITypes {

    /**
     * In Retrofit architecture I define static method in ApplicationController class that will
     * take advantage of factory design pattern and create different RetrofitApi subclasses based on
     * enum we provide as an argument. See <>http://coders-mill.com/retrofit-in-android-app/<>
     **/

    MOVIES_API(new MoviesAPI());

    private final ARetrofitAPI instance;

    private APITypes(ARetrofitAPI instance) {
        this.instance = instance;
    }

    public ARetrofitAPI getApiType() {
        return instance;
    }

}