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

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "reviews")
public class Review extends Model {

    // A movie has many trailers so lets associate each movie to a trailer in a many-to-one relation
    //ActiveAndroid Annotations
    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    public Movie movie;
    // Gson annotations
    @SerializedName("author")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "author")
    private String author;
    // Gson annotations
    @SerializedName("content")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "content")
    private String content;
    // Gson annotations
    @SerializedName("url")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "url")
    private String url;
    // Gson annotations
    @SerializedName("id")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "id_", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String id_;


    public Review() {
        // You have to call super in each constructor to create the table.
        super();
    }

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The id
     */
    public String getId_() {
        return id_;
    }

    /**
     * @param id_ The id
     */
    public void setId(String id_) {
        this.id_ = id_;
    }

    /**
     * @return The movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @param movie The movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}