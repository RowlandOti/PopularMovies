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

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public enum ESortType {

    // All the image sizes that the application will query
    SORT_POPULARITY("popularity.desc"),
    SORT_HIGHEST_RATED("vote_average.desc"),
    SORT_FAVORITED("favorited");

    private String sortType;

    private ESortType(String sortType) {
        sortType = sortType;
    }
    // Get the url corresponding to the enum
    public String getSortType() {
        return sortType;
    }
}
