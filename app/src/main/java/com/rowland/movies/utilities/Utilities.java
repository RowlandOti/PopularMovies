package com.rowland.movies.utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rowland on 7/7/2015.
 */
public class Utilities {

    /**
     * Class used for storing shared preferecences data in the app
     * Created by Otieno Rowland on 20/9/2015. <email>rowland@skyllabler.com</email>  on 20/9/2015
     */
    public static class PreferenceUtility {

        public static void savePrefs(Context context, String key, boolean value) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(key, value);
            edit.commit();
        }

        public static void savePrefs(Context context, String key, int value) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt(key, value);
            edit.commit();
        }

        public static void savePrefs(Context context, String key, long value) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sp.edit();
            edit.putLong(key, value);
            edit.commit();
        }

        public static void savePrefs(Context context, String key, String value) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(key, value);
            edit.commit();
        }

        public static String getPrefs(Context context, String key, String defaultValue) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getString(key, defaultValue);
        }

        public static int getPrefs(Context context, String key, int defaultValue) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getInt(key, defaultValue);
        }

        public static float getPrefs(Context context, String key, float defaultValue) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getFloat(key, defaultValue);
        }

        public static boolean getPrefs(Context context, String key, boolean defaultValue) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getBoolean(key, defaultValue);
        }
    }

    /**
     * Class is used to provide network availability information
     */
    public static class NetworkUtility {
        /**
         * Returns true if network is available or about to become available
         */
        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        public static boolean isNetworkInWifiOrWimaxOrMobileAvailable(Context context) {
            // Check for mobile and Wifi networks
            boolean isMobile = false;
            boolean isWifi = false;
            boolean isWimax = false;
            // Grasp an instance of ConnectivityManager
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // This List will contain all Network information
            List<NetworkInfo> infoAvailableNetworks = new ArrayList<NetworkInfo>();
            // Should be Marshmarllow or higher
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                // Put all available networks into an array
                Network[] availableNetworks = cm.getAllNetworks();
                // Now add each network information
                for (Network availableNetwork : availableNetworks) {
                    infoAvailableNetworks.add(cm.getNetworkInfo(availableNetwork));
                }
            } else {
                infoAvailableNetworks = Arrays.asList(cm.getAllNetworkInfo());
            }

            if (infoAvailableNetworks != null) {
                for (NetworkInfo network : infoAvailableNetworks) {

                    if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (network.isConnected() && network.isAvailable())
                            isWifi = true;
                    }
                    if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
                        if (network.isConnected() && network.isAvailable())
                            isMobile = true;
                    }
                    if (network.getType() == ConnectivityManager.TYPE_WIMAX) {
                        if (network.isConnected() && network.isAvailable())
                            isWimax = true;
                    }
                }
            }

            return isMobile || isWifi || isWimax;
        }

    }
}
