package com.example.better_waves;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean loginMode = true;
    TextView signUp;

    public void showSongList(){
        Intent intent = new Intent(getApplicationContext(),SongListActivity.class);
        startActivity(intent);

    }
    public void onClick(View view){
        if(view.getId() == R.id.signUp){

            Button login = (Button) findViewById(R.id.login);

            if(loginMode){
                loginMode = false;
                login.setText("SignUp");
                signUp.setText("Login");
            }
            else{
                loginMode = true;
                login.setText("Login");
                signUp.setText("SignUp");
            }
        }
    }
    public void login (View view){

        EditText username = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.password);

        if( username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(this,"A username and password required",Toast.LENGTH_SHORT).show();
        }
        else{
            if(loginMode) {
                Toast.makeText(this, "  abhi DataBase baki hee", Toast.LENGTH_SHORT).show();
                showSongList();
            }
            else {
                Toast.makeText(this,"User is added! ENJOY!!!",Toast.LENGTH_SHORT).show();
                showSongList();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
    }
}
