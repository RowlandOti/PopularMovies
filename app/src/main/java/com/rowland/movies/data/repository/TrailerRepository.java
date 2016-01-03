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

import android.util.Log;

import com.activeandroid.query.Select;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.rest.collections.TrailerCollection;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Trailer;

import java.util.List;

/**
 * Created by Oti Rowland on 12/22/2015.
 */
public class TrailerRepository {

    // The class Log identifier
    private static final String LOG_TAG = TrailerRepository.class.getSimpleName();

    // The default constructor
    public TrailerRepository() {
    }

    public List<Trailer> getAllWhere(Movie movie) {
        // Holds the where clause
        String whereClause = "trailers.movie = ?";
        if(movie != null) {
            // ToDo: Move this logic to the Movie model where it belongs
            // Query ActiveAndroid for list of data
            List<Trailer> queryResults = new Select()
                    .from(Trailer.class)
                    //.innerJoin(Movie.class)
                    //.on("trailers.movie = movies.id")
                    //.where(whereClause, movie.getId())
                    .orderBy("id ASC")
                    .limit(4).execute();
            // This is how you execute a query
            return queryResults;
        }
        return  null;
    }

    // Save the movie list
    public void saveAll(TrailerCollection trailerCollection) {
        // Acquire the related movie model
        Movie movie = new Select()
                .from(Movie.class)
                .where("id_ = ?", trailerCollection.getId()).executeSingle();

        for (Trailer trailer : trailerCollection.getResults()) {
            // Does a trailer exist
            if (trailer.getId_() != null) {
                // Set any necessary details
                trailer.setMovie(movie);
                // Check if is duplicate
                boolean iSExistingTrailer = new Select()
                        .from(Trailer.class)
                        .where("id_ = ?", trailer.getId_()).exists();
                // Check whether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Movie: " + iSExistingTrailer);
                }
                // Save only new movies to the database
                if (!iSExistingTrailer) {
                    // Save movie
                    trailer.save();
                    // Check wether we are in debug mode
                    if (BuildConfig.IS_DEBUG_MODE) {
                        Log.d(LOG_TAG, "Movie: " + trailer.getName());
                    }
                }
            }
        }
    }
}