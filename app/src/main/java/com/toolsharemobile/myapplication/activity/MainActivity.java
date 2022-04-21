package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.toolsharemobile.myapplication.R;

public class MainActivity extends AppCompatActivity {


    AuthUser authUser;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpButtonToProfile();
        setUpButtonToSignUp();
    }


    public void setUpButtonToProfile(){



        LinearLayout buttonToLogin = findViewById(R.id.buttonSplashToLogin);

        buttonToLogin.setOnClickListener(view -> {

            if (Amplify.Auth.getCurrentUser() != null) {
                authUser = Amplify.Auth.getCurrentUser();
                username = authUser.getUsername();
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setUpButtonToSignUp(){


        LinearLayout buttonToSignUp = findViewById(R.id.buttonSplashToSignUp);


        buttonToSignUp.setOnClickListener(view -> {

            if (Amplify.Auth.getCurrentUser() != null) {
                authUser = Amplify.Auth.getCurrentUser();
                username = authUser.getUsername();
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

}