package com.renanparis.ceed.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.renanparis.ceed.R;
import com.renanparis.ceed.ui.activity.preferences.NotesPreferences;
import com.renanparis.ceed.ui.activity.preferences.SplashScreenPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String KEY_PREFERENCES = "first_time_app";
    private Handler handle;
    private SplashScreenPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handle = new Handler();
        preferences = new SplashScreenPreferences(this);
        configSplashScreenDelay();

    }

    private void configSplashScreenDelay() {
        if (preferences.contains(KEY_PREFERENCES)){
            delayHalfSecond();

        }else {
            delayTwoSeconds();
            preferences.addPreferenceNotFirstTime(KEY_PREFERENCES);

        }
    }

    private void delayHalfSecond() {
        handle.postDelayed(this::goToListNotes, 500);
    }

    private void delayTwoSeconds() {
        handle.postDelayed(this::goToListNotes, 2000);
    }

    private void goToListNotes() {
        Intent goToListNotes = new Intent(this, ListNotesActivity.class);
        startActivity(goToListNotes);
        finish();
    }
}
