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

package com.rowland.movies.data.broadcastrecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.rowland.movies.BuildConfig;

/**
 * Created by Oti Rowland on 12/21/2015.
 *
 * This above broadcast receiver will be called only when Network state change
 * to connected and not on disconnected.
 */
public class NetworkChangeBroadCastReceiver extends BroadcastReceiver {

    // The class Log identifier
    private static final String LOG_TAG = NetworkChangeBroadCastReceiver.class.getSimpleName();

    public NetworkChangeBroadCastReceiver(Context context)
    {
        // Create an IntentFilter
        IntentFilter intentFilter = new IntentFilter();
        // Declare the type of Action for the IntentFilter
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        // Register reciever to listen for above IntentFilter
        context.registerReceiver(this, intentFilter);
    }
    // Inform if contents of database is changed
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Check wether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Connection broadcast fired from " + mLoader.getClass().getSimpleName());
        }
    }
}
