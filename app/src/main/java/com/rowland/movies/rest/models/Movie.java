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

package com.rowland.movies.rest.models;

/**
 * Generated with love from <>http://www.jsonschema2pojo.org/</>
 * Created by Rowland on 12/11/2015.
 */

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rowland.movies.BuildConfig;

import java.util.Date;
import java.util.List;

@Table(name = "movies")
public class Movie extends Model {

    // Gson annotations
    @SerializedName("release_date")
    //ActiveAndroid Annotations
    @Column(name = "release_date")
    public Date releaseDate;
    // Movie owns these items
    public List<Review> movieReviews;
    public List<Trailer> movieTrailers;
    // Gson annotations
    @SerializedName("poster_path")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "poster_path")
    private String posterPath;
    // Gson annotations
    @SerializedName("overview")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "overview")
    private String overview;
    // Gson annotations
    @SerializedName("original_title")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "originalTitle")
    private String originalTitle;
    // Gson annotations
    @SerializedName("original_language")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "originalLanguage")
    private String originalLanguage;
    // Gson annotations
    @SerializedName("title")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "title")
    private String title;
    // Gson annotations
    @SerializedName("backdrop_path")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "backdropPath")
    private String backdropPath;
    // Gson annotations
    @SerializedName("vote_count")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "voteCount")
    private Integer voteCount;
    // Gson annotations
    @SerializedName("id")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "id_", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Integer id_;
    // Gson annotations
    @SerializedName("popularity")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "popularity")
    private Double popularity;
    // Gson annotations
    @SerializedName("vote_average")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "voteAverage")
    private Double voteAverage;
    // Gson annotations
    @SerializedName("adult")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "adult")
    private Boolean adult;
    //ActiveAndroid Annotations
    @Column(name = "isPopular")
    private Boolean isPopular;
    //ActiveAndroid Annotations
    @Column(name = "isHighestRated")
    private Boolean isHighestRated;
    //ActiveAndroid Annotations
    @Column(name = "isFavourite")
    private Boolean isFavourite;


    // The class Log identifier
    private static final String LOG_TAG = Movie.class.getSimpleName();

    public Movie() {
        // You have to call super in each constructor to create the table.
        super();
    }

    // Retrieve all the movie owned trailers
    public List<Trailer> getMovieTrailers() {
        return getMany(Trailer.class, "Trailer");
    }

    // Retrieve all the movie owned reviews
    public List<Review> getMovieReviews() {
        return getMany(Review.class, "Review");
    }
    /**
     * @return The isHighestRated
     */
    public boolean getIsHighestRated() {
        return isHighestRated;
    }

    /**
     * @param isHighestRated The isHighestRated
     */
    public void setIsHighestRated(boolean isHighestRated) {
        this.isHighestRated = isHighestRated;
        // Check wether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "HighestRated: " + isHighestRated);
        }
    }

    /**
     * @return The isPopular
     */
    public boolean getIsFavourite() {
        return isFavourite;
    }

    /**
     * @param isFavourite The isPopular
     */
    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
    /**
     * @return The isPopular
     */
    public boolean getIsPopular() {
        return isPopular;
    }

    /**
     * @param isPopular The isPopular
     */
    public void setIsPopular(boolean isPopular) {
        this.isPopular = isPopular;
    }

    /**
     * @return The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return The overview
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The releaseDate
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * @return The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * @param originalLanguage The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @param backdropPath The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * @return The voteCount
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * @param voteCount The vote_count
     */
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * @return The id
     */
    public Integer getId_() {
        return id_;
    }

    /**
     * @param id_ The id
     */
    public void setId(Integer id_) {
        this.id_ = id_;
    }

    /**
     * @return The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * @param popularity The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     * @return The voteAverage
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * @return The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * @param adult The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

}