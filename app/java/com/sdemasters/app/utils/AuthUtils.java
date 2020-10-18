package com.sdemasters.app.utils;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sdemasters.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthUtils{
    public static void getAccessToken(final Context context, GoogleSignInAccount account){
        final String[] access_token = new String[1];
        final VolleySingleton volleySingleton = VolleySingleton.getInstance(context);
        try{
	    // api call to get google access token from ServerAuthCode
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("grant_type", "authorization_code");
            jsonObject.put("client_id", context.getString(R.string.google_client_id));
            jsonObject.put("client_secret",context.getString(R.string.google_client_secret));
            jsonObject.put("redirect_uri","");
            jsonObject.put("code", account.getServerAuthCode());


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.googleapis.com/oauth2/v4/token", jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                       access_token[0] = response.getString("access_token");
                            try {
				// api call to convert Google access token to Django backend refresh token
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("grant_type", "convert_token");
                                jsonObject1.put("client_id", context.getString(R.string.backend_client_id));
                                jsonObject1.put("client_secret", context.getString(R.string.backend_client_secret));
                                jsonObject1.put("backend", "google-oauth2");
                                jsonObject1.put("token", access_token[0]);
                                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, "http://192.168.0.10:8000/auth/convert-token", jsonObject1, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String django_access_token = response.getString("access_token");
                                            String refresh_token = response.getString("refresh_token");
                                            SharedPreferences sharedPref = context.getSharedPreferences(
                                                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putBoolean(context.getString(R.string.is_logged_in), true);
                                            editor.putString(context.getString(R.string.access_token), django_access_token);
                                            editor.putString(context.getString(R.string.refresh_token), refresh_token);
                                            System.out.println("refresh_token "+refresh_token);
                                            editor.apply();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });
                                volleySingleton.getRequestQueue().add(jsonObjectRequest1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse (VolleyError error){
                    System.out.println(error.toString());
                }
            });

            volleySingleton.getRequestQueue().add(jsonObjectRequest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}