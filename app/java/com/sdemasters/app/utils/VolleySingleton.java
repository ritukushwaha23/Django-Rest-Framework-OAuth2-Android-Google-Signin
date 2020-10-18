package com.sdemasters.app.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//A Singleton Class to make a Volley Instance & requestQueue that is shared by all activities, and it's lifetime is same as app's lifetime
public class VolleySingleton {

    private static VolleySingleton instance;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return  instance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}