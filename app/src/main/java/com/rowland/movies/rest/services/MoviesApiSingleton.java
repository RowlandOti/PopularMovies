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

/**
 * Created by Oti Rowland on 12/11/2015.
 */
public class MoviesAPISingleton extends ARetrofitAPI{

    /**
     * I should only ever call retrofit.create() once and re-use the
     * same instance of MoviesAPIService every time you need to interaction with it.
     *
     * I used the regular singleton pattern in order to ensure that there only is ever a single
     * instance of this class that I use everywhere. A dependency injection framework would
     * also be something that I could used to manage these instances but would be a bit overkill since
     * I am not already utilizing it.
     */

    // Declare MoviesAPIService singleton instance
    private static MoviesAPIService imDbEndPointsApiInstance;

    private MoviesAPISingleton()
    {
        // Initialise the singleton instance
        imDbEndPointsApiInstance = retrofit.create(MoviesAPIService.class);
    }
    // Return the singleton instance
    public static MoviesAPIService getInstance()
    {
        return imDbEndPointsApiInstance;
    }
}
