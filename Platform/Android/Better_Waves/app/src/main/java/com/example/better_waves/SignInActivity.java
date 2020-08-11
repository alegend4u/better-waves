package com.example.better_waves;

import android.content.Intent;
import android.content.res.ColorStateList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.UserToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    boolean loginMode = true;
    private View loginView;
    private View signUpView;
    private int shortAnimationDuration;
    private Button loginButton;
    private Button signUpButton;

    private ColorStateList defaultButtonBack;
    private int defaultButtonColors;
    private ColorStateList mainButtonBack;
    private int mainButtonColors;

    public SignInActivity() {
    }

    public void Authenticate() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        if (loginMode) {
            final EditText username = findViewById(R.id.userName);
            final EditText password = findViewById(R.id.password);

            if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
                Toast.makeText(this, "Credentials required", Toast.LENGTH_SHORT).show();
            } else {
//            Authenticate the user
                String base_url = getApplicationContext().getResources().getString(R.string.base_url);
                String token_url = base_url + "api-token-auth/";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, token_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject jo = new JsonParser().parse(response).getAsJsonObject();
                        if (jo.has("token")) {
                            String user_token = jo.get("token").getAsString();
                            UserToken.setToken(user_token);
                            System.out.println("Token: " + UserToken.getToken());

                            String greeting = "Welcome back " + username.getText().toString();
                            Toast.makeText(getApplicationContext(), greeting, Toast.LENGTH_SHORT).show();

                            Authenticate();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            System.err.println("Invalid Credentials.");
                        }
                    }
                }, new Response.ErrorListener() {
                    String message;
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            message = "Connection Error";
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            NetworkResponse response = error.networkResponse;
                            if(response != null && response.data != null){
                                switch(response.statusCode){
                                    case 400:
                                        message = "Invalid Credentials";
                                        break;
                                    case 500:
                                        message = "Server Error";
                                        break;
                                    case 403:
                                        message = "Authentication Error";
                                        break;
                                    default:
                                        message = "Error";
                                }
                            }
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            message = "Network Error";
                        }
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> creds = new HashMap<>();
                        creds.put("username", username.getText().toString());
                        creds.put("password", password.getText().toString());
                        return creds;
                    }
                };
                queue.add(stringRequest);
            }
        } else {
//            Switch to login mode
            switchMode();
        }
    }

    public void signup(View view) {
        if (!loginMode){
            final String first_name = ((EditText) findViewById(R.id.fname)).getText().toString();
            final String last_name = ((EditText) findViewById(R.id.lname)).getText().toString();
            final String username = ((EditText) findViewById(R.id.new_userName)).getText().toString();
            final String email = ((EditText) findViewById(R.id.email)).getText().toString();
            final String password = ((EditText) findViewById(R.id.new_password)).getText().toString();

            if (first_name.equals("")
                || last_name.equals("")
                || username.equals("")
                || email.equals("")
                || password.equals("")
            ){
                Toast.makeText(getApplicationContext(), "Kindly fill the form properly",
                        Toast.LENGTH_SHORT).show();
            } else {
                String base_url = getApplicationContext().getResources().getString(R.string.base_url);
                String signup_url = base_url + "signup";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, signup_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "User Created! Kindly Login", Toast.LENGTH_SHORT).show();
                        switchMode();
                    }
                }, new Response.ErrorListener() {
                    String message;
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            message = "Connection Error";
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            NetworkResponse response = error.networkResponse;
                            if(response != null && response.data != null){
                                switch(response.statusCode){
                                    case 400:
                                        message = "Username already exists!";
                                        break;
                                    case 500:
                                        message = "Server Error";
                                        break;
                                    case 403:
                                        message = "Authentication Error";
                                        break;
                                    default:
                                        message = "Error";
                                }
                            }
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            message = "Network Error";
                        }
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> creds = new HashMap<>();
                        creds.put("first_name", first_name);
                        creds.put("last_name", last_name);
                        creds.put("username", username);
                        creds.put("email", email);
                        creds.put("password", password);
                        return creds;
                    }
                };
                queue.add(stringRequest);
            }
        } else {
//            Switch to sign up mode
            switchMode();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginView = findViewById(R.id.loginForm);
        signUpView = findViewById(R.id.signUpForm);

        signUpButton = findViewById(R.id.signup);
        loginButton = findViewById(R.id.login);

        defaultButtonBack = signUpButton.getBackgroundTintList();
        defaultButtonColors = signUpButton.getCurrentTextColor();
        mainButtonBack = loginButton.getBackgroundTintList();
        mainButtonColors = loginButton.getCurrentTextColor();
//        Set login mode by default
        loginMode = true;
        signUpView.setVisibility(View.GONE);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime)*2;
    }

    public void switchMode(){
        if (loginMode){
//            Show sign up form
            loginView.setAlpha(1f);
            loginView.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
            loginView.setVisibility(View.INVISIBLE);

            signUpView.setAlpha(0f);
            signUpView.setVisibility(View.VISIBLE);
            signUpView.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);

            signUpButton.setBackgroundTintList(mainButtonBack);
            signUpButton.setTextColor(mainButtonColors);
            loginButton.setBackgroundTintList(defaultButtonBack);
            loginButton.setTextColor(defaultButtonColors);

            loginMode = false;
        } else {
//            Show login form
            signUpView.setAlpha(1f);
            signUpView.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
            signUpView.setVisibility(View.INVISIBLE);

            loginView.setAlpha(0f);
            loginView.setVisibility(View.VISIBLE);
            loginView.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);

            signUpButton.setBackgroundTintList(defaultButtonBack);
            signUpButton.setTextColor(defaultButtonColors);
            loginButton.setBackgroundTintList(mainButtonBack);
            loginButton.setTextColor(mainButtonColors);

            loginMode = true;
        }

    }
}
