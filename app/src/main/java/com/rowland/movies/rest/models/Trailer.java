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

@Table(name = "trailers")
public class Trailer extends Model {

    // A movie has many trailers so lets associate each movie to a trailer in a many-to-one relation
    //ActiveAndroid Annotations
    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    public Movie movie;
    // Gson annotations
    @SerializedName("name")
    @Expose
    // ActiveAndroid Annotations
    @Column(name = "name")
    private String name;
    // Gson annotations
    @SerializedName("site")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "site")
    private String site;
    // Gson annotations
    @SerializedName("type")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "type")
    private String type;
    // Gson annotations
    @SerializedName("id_")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "id_", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Double id_;
    // Gson annotations
    @SerializedName("key")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "key")
    private String key;
    // Gson annotations
    @SerializedName("size")
    @Expose
    //ActiveAndroid Annotations
    @Column(name = "size")
    private Integer size;

    public Trailer() {
        // You have to call super in each constructor to create the table.
        super();
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The id_
     */
    public Double getId_() {
        return id_;
    }

    /**
     * @param id The id
     */
    public void setId(Double id) {
        this.id_ = id_;
    }

    /**
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

}