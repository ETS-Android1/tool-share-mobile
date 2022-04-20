package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.toolsharemobile.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginLoginButtonSetUp();
        setUpButtonToSignUp();

    }

    public void loginLoginButtonSetUp(){
        LinearLayout buttonLoginSubmit = findViewById(R.id.buttonLoginSubmit);
        Intent gettingIntent = getIntent();
        String email = gettingIntent.getStringExtra(VerifyAccountActivity.TAG_VERIFY);
        EditText loginTextEmailAddress = findViewById(R.id.loginTextEmailAddress);
        EditText loginTextPassword = findViewById(R.id.loginTextPassword);
        loginTextEmailAddress.setText(email);
        buttonLoginSubmit.setOnClickListener(view -> {
            String userEmail = loginTextEmailAddress.getText().toString();
            String password = loginTextPassword.getText().toString();

            Amplify.Auth.signIn(
                    userEmail,
                    password,

                    success -> {
                        Log.i(TAG, "Login completed: " + success);
                        System.out.println("Login Button!");
                        Intent goToLoginIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                        LoginActivity.this.startActivity(goToLoginIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Login not completed: " + failure);
                        runOnUiThread(()->
                        {
                            Toast.makeText(LoginActivity.this, "Login not successful! Check Email or Password", Toast.LENGTH_SHORT);
                        });
                    }

            );



            //TODO Add Login

        });
    }


    public void setUpButtonToSignUp(){


        LinearLayout buttonToSignUp = findViewById(R.id.buttonLoginToSignUp);

        buttonToSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);



        });

    }
}