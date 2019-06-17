package com.renanparis.ceed.ui.activity.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesPreferences {

    public static final String LAYOUT_MODE = "linear_mode";
    public static final String NOTES_PREFERENCES = "com.renanparis.ceed.ui.activity.preferences.NotesPreferences";
    private Context context;

    public NotesPreferences(Context context) {
        this.context = context;
    }

    public void setLinearMode(boolean value){

        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LAYOUT_MODE, value);
        editor.commit();


    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(NOTES_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean isLinearMode(){

        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(LAYOUT_MODE, false);
    }


}
