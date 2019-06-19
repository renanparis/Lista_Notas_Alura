package com.renanparis.ceed.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.renanparis.ceed.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToListNotes();
            }
        }, 2000);

    }

    private void goToListNotes() {
        Intent goToListNotes = new Intent(this, ListNotesActivity.class);
        startActivity(goToListNotes);
        finish();
    }
}
