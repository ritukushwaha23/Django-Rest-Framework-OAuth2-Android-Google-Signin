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

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

	//check if user is already logged in using shared preferences flag
	
        if(sharedPref.contains("is_logged_in")){         //if user is logged in
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }

	//if failed, present login screen
        else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }

	//TODO:
	//Add code for checking validity of access token
	//If access_token is expired, then refresh the access token, using refresh token
	//If refresh_token is also expired, present google signin popup again and get new access token using convert-token endpoint.
    }
}