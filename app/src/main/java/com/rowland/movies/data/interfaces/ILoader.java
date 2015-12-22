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

package com.rowland.movies.data.interfaces;

import android.content.Context;

import com.rowland.movies.rest.models.Movies;
import com.rowland.movies.rest.services.IMoviesAPIService;

import java.util.List;

/**
 * Created by Oti Rowland on 12/21/2015.
 *
 * All loaders will implement this class
 */
public interface ILoader<T> {

    // Implement to retrive data from online
    void getOnlineData(IMoviesAPIService movieService);
    // Implement to retrive data from local cache
    List<T> getLocalData();
    // Get online status
    boolean getIsOnline();
    // Set online status
    void setIsOnline(boolean isOnline);
    // Get the Context
    Context getContext();

}
