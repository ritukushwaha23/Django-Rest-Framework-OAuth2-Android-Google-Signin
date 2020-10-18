package com.sdemasters.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //TODO check for access token validity, if it is invalid/expired refresh the access token using refresh token
        //TODO if refresh token is invalid, follow the oauth2 flow again.

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.contains("is_logged_in")){         //if user is logged in
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }
}