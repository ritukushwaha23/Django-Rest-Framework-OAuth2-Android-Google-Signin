package com.sdemasters.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button logout_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                Toast.makeText(MainActivity.this, sharedPref.getString(getString(R.string.access_token), null), Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(getString(R.string.is_logged_in));
                editor.remove(getString(R.string.access_token));
                editor.remove(getString(R.string.refresh_token));
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}