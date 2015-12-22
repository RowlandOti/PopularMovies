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

package com.rowland.movies.data.repository;

import com.activeandroid.query.Select;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movie;

import java.util.List;

/**
 * Created by Oti Rowland on 12/22/2015.
 *
 * Movie Repository
 */
public class MovieRepository {

    public MovieRepository() {

    }

    public List<Movie> getAllByOrderOf(ESortOrder sortOrder) {
        // Holds the where clause
        String whereClause = "";
        // Find out which where clause to use
        switch (sortOrder) {
            case POPULAR_DESCENDING:
                whereClause = "isPopular = ?";
                break;
            case HIGHEST_RATED_DESCENDING:
                whereClause = "isHighestRated = ?";
                break;
            case FAVOURITE_DESCENDING:
                whereClause = "isFavourite = ?";
                break;
        }

        // Query ActiveAndroid for list of data
        List<Movie> queryResults = new Select()
                .from(Movie.class).where(whereClause, true)
                .orderBy("id_ ASC")
                .limit(100).execute();
        // This is how you execute a query
        return queryResults;
    }
}
