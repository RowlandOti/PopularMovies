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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class RestError {

    // Gson annotations
    @SerializedName("code")
    @Expose
    private Integer code;

    // Gson annotations
    @SerializedName("error_message")
    @Expose
    private String strMessage;

    public RestError(String strMessage) {
        this.strMessage = strMessage;
    }

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The code
     */
    public String getStrMesage() {
        return strMessage;
    }

    /**
     * @param strMessage The strMessage
     */
    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }
}
