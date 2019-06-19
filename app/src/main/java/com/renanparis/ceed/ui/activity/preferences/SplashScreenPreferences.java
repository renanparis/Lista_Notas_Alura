package com.renanparis.ceed.ui.activity.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SplashScreenPreferences {

    public static final String SPLASH_SCREEN_PREFERENCES = "com.renanparis.ceed.ui.activity.preferences.SplashScreenPreferences";
    private Context context;

    public SplashScreenPreferences(Context context) {
        this.context = context;
    }

    public boolean contains(String key) {

        SharedPreferences preferences = getSharedPreferences();
        return preferences.contains(key);
    }

    public void addPreferenceNotFirstTime(String key) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, true);
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SPLASH_SCREEN_PREFERENCES, Context.MODE_PRIVATE);
    }
}
