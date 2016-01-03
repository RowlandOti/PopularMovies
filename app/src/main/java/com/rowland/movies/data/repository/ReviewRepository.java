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
import com.rowland.movies.rest.collections.ReviewCollection;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Review;

import java.util.List;

/**
 * Created by Oti Rowland on 12/22/2015.
 */
public class ReviewRepository {

    // The class Log identifier
    private static final String LOG_TAG = ReviewRepository.class.getSimpleName();

    // The default constructor
    public ReviewRepository() {
    }

    public List<Review> getAllWhere(Movie movie) {
        // Holds the where clause
        String whereClause = "reviews.movie = ?";
        if (movie != null) {
            // ToDo: Move this logic to the Movie model where it belongs
            // Query ActiveAndroid for list of data
            List<Review> queryResults = new Select()
                    .from(Review.class)
                    .where(whereClause, movie.getId())
                    .orderBy("id ASC")
                    .limit(4).execute();
            // This is how you execute a query
            return queryResults;
        }
        return null;
    }

    // Save the movie list
    public void saveAll(ReviewCollection reviewCollection) {
        // Acquire the related movie model
        Movie movie = new Select()
                .from(Movie.class)
                .where("id_ = ?", reviewCollection.getId()).executeSingle();
        // Save the reviews
        for (Review review : reviewCollection.getResults()) {
            // Does a review exist
            if (review.getId_() != null) {
                // Set any necessary details
                review.setMovie(movie);
                // Check if is duplicate
                boolean iSExistingReview = new Select()
                        .from(Review.class)
                        .where("id_ = ?", review.getId_()).exists();
                // Check whether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Movie: " + iSExistingReview);
                }
                // Save only new movies to the database
                if (!iSExistingReview) {
                    // Save movie
                    review.save();
                    // Check wether we are in debug mode
                    if (BuildConfig.IS_DEBUG_MODE) {
                        Log.d(LOG_TAG, "Movie: " + review.getAuthor());
                    }
                }
            }
        }
    }

}