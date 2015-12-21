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

package com.rowland.movies.asynctaskloaders.broadcastrecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;


/**
 * Created by Oti Rowland on 12/21/2015.
 */
public class BaseLoaderBroadCastReceiver extends BroadcastReceiver
{
    final private Loader mLoader;

    public BaseLoaderBroadCastReceiver(Loader loader)
    {
        this.mLoader = loader;
        // Register for events related to sdcard installation.
        IntentFilter mLFilter = new IntentFilter();
        mLFilter.addAction("RELOADER_DATA");
        mLoader.getContext().registerReceiver(this, mLFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mLoader.onContentChanged();
    }
}
