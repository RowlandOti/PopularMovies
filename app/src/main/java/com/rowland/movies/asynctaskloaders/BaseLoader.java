

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

package com.rowland.movies.asynctaskloaders;

import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.uwetrottmann.androidutils.GenericSimpleLoader;

import java.util.List;

public abstract class BaseLoader<T> extends GenericSimpleLoader<T> {


    public BaseLoader(Context context) {
        super(context);
    }
    // Checking for takeContentChanged seems an important step too
    // See http://stackoverflow.com/a/19480980/1464571
    @Override
    protected void onStartLoading() {
        if (mItems != null) {
            deliverResult(mItems);
        }

        if (takeContentChanged() || mItems == null) {
            forceLoad();
        }
    }
}
