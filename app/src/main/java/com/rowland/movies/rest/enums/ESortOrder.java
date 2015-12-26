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

package com.rowland.movies.rest.enums;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public enum ESortOrder {
    // The sort order for retrieving the movies
    POPULAR_DESCENDING("popularity.desc"), HIGHEST_RATED_DESCENDING("vote_average.desc"), FAVOURITE_DESCENDING("favourite.desc");
    // Sort order string
    private String sortOrder;
    // Is what type of sort
    private boolean isHighestRated = false;
    private boolean isPopular = false;
    private boolean isFavourite = false;

    private ESortOrder(String s) {
        sortOrder = s;
    }
    // Get the corresponding sort order
    private void setSortOrderType(ESortOrder sortOrderType) {
        // Find out which sort this is
        switch (sortOrderType) {
            case POPULAR_DESCENDING:
                this.isPopular = true;
                break;
            case HIGHEST_RATED_DESCENDING:
                this.isHighestRated = true;
                break;
            case FAVOURITE_DESCENDING:
                this.isFavourite = true;
                break;
        }
    }
    // Get the correspinding sort order
    public String getSortOrder() {
        return this.sortOrder;
    }
    // Get whether we sorted by popularity
    public boolean isPopular() {
        setSortOrderType(this);
        return isPopular;
    }
    // Get whether we sorted by rating
    public boolean isHighestRated() {
        setSortOrderType(this);
        return isHighestRated;
    }
    // Get whether we sorted by favourite
    public boolean isFavourite() {
        setSortOrderType(this);
        return isFavourite;
    }


}
