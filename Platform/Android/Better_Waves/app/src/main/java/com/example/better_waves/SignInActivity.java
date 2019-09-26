package com.example.better_waves;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.better_waves.ui.main.UserToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    Boolean loginMode = true;
    TextView signUp;

    public SignInActivity() {
    }

    public void Authenticate() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.signUp) {
            Button login = (Button) findViewById(R.id.login);
            if (loginMode) {
                loginMode = false;
                login.setText("Sign Up");
                signUp.setText("Log In");
            } else {
                loginMode = true;
                login.setText("Log In");
                signUp.setText("Sign Up");
            }
        }
    }

    public void login(View view) {

        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.password);

        if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "Credentials required", Toast.LENGTH_SHORT).show();
        } else {
            if (loginMode) {

//                Authenticate the user
                String base_url = getApplicationContext().getResources().getString(R.string.base_url);
                String token_url = base_url + "api-token-auth/";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, token_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonObject = new String(response);
                        JsonObject jo = new JsonParser().parse(jsonObject).getAsJsonObject();
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
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> creds = new HashMap<String, String>();
                        creds.put("username", username.getText().toString());
                        creds.put("password", password.getText().toString());
                        return creds;
                    }
                };
                queue.add(stringRequest);
            } else {
                String greeting = "Welcome " + username.getText().toString();
                Toast.makeText(this, greeting, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
    }
}
