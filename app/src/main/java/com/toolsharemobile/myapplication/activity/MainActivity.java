package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.toolsharemobile.myapplication.R;

public class MainActivity extends AppCompatActivity {


    LinearLayout buttonToLogin, buttonToSignUp;
    Animation atg, btgone, btgtwo;
    AuthUser authUser;
    CardView ivSplash;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpButtonToProfile();
        setUpButtonToSignUp();





        ivSplash = findViewById(R.id.imageViewSplashLogo);
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone = AnimationUtils.loadAnimation(this, R.anim.btgone);
        btgtwo = AnimationUtils.loadAnimation(this, R.anim.btgtwo);

        ivSplash.startAnimation(atg);
        buttonToLogin.startAnimation(btgone);
        buttonToSignUp.startAnimation(btgtwo);
    }


    public void setUpButtonToProfile(){

        buttonToLogin = findViewById(R.id.buttonSplashToLogin);

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


        buttonToSignUp = findViewById(R.id.buttonSplashToSignUp);


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