package com.rowland.movies.utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Rowland on 7/7/2015.
 */
public class Utilities {

    /**
     * Class used for storing shared preferecen data in the app
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
}
