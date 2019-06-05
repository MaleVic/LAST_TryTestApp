package com.example.gamesmannna.trytestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gamesmannna.trytestapp.Common.Common;

public class MainActivity extends AppCompatActivity {

    Button SignUpButton, SignInButton;
    TextView txtSlogan;
    public  static Common Common;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInButton = (Button)findViewById(R.id.SignInButton);
        SignUpButton =(Button)findViewById(R.id.SignUpButton);

        txtSlogan = (TextView)findViewById(R.id.txtSlogan);

        Common=new Common();

    SignInButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signIn = new Intent(MainActivity.this,SignIn.class);
            startActivity(signIn);
        }
    });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);
            }
        });

    }

}

