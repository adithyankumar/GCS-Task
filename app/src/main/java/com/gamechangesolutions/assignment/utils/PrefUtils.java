package com.gamechangesolutions.assignment.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gamechangesolutions.assignment.BuildConfig;

/**
 * {@link PrefUtils} is utility class used for saving and retrieving {@link SharedPreferences}
 * data
 */
public class PrefUtils {
    private static final String PREF = BuildConfig.APPLICATION_ID;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences preferences;
    private static final String TAG = PrefUtils.class.getSimpleName();


    public PrefUtils(Context context) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        boolean status = editor.commit();
        Log.i(TAG, "setString: commit " + status);
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
        boolean status = editor.commit();
        Log.i(TAG, "setInt: commit " + status);
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void setFloat(String key, float value) {
        editor.putFloat(key, value);
        boolean status = editor.commit();
        Log.i(TAG, "setFloat: commit " + status);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        boolean status = editor.commit();
        Log.i(TAG, "setBoolean: commit " + status);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }


}
