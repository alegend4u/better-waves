package com.example.better_waves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean loginMode = true;
    TextView signUp;

    public void showSongList(){
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        System.out.println("Starting main");
        startActivity(intent);
    }
    public void onClick(View view){
        if(view.getId() == R.id.signUp){
            Button login = (Button) findViewById(R.id.login);
            if(loginMode){
                loginMode = false;
                login.setText("Sign Up");
                signUp.setText("Log In");
            }
            else{
                loginMode = true;
                login.setText("Log In");
                signUp.setText("Sign Up");
            }
        }
    }
    public void login (View view){

        EditText username = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.password);

        if( username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(this,"Credentials required",Toast.LENGTH_SHORT).show();
        }
        else{
            if(loginMode) {
                String greeting = "Welcome back" + username.getText().toString();
                Toast.makeText(this, greeting, Toast.LENGTH_SHORT).show();
                showSongList();
            }
            else {
                String greeting = "Welcome" + username.getText().toString();
                Toast.makeText(this, greeting,Toast.LENGTH_SHORT).show();
                showSongList();
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
