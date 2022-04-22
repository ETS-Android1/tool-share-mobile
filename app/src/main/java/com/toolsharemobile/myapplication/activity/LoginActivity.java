package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.toolsharemobile.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "LOGIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginLoginButtonSetUp();
        logoutButtonSetup();
        setUpButtonToSignUp();
        setUpNavBar();
        setUpCreateToolNavigation();
    }

    @Override
    protected void onResume(){
        super.onResume();

        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        String preferredUserName = "";
        LinearLayout buttonLoginSubmit = findViewById(R.id.buttonLoginSubmit);
        LinearLayout buttonLogout = findViewById(R.id.buttonLogout);
        LinearLayout buttonLoginToSignUp = findViewById(R.id.buttonLoginToSignUp);
        TextInputLayout editText = findViewById(R.id.editText);
        TextInputLayout editText2 = findViewById(R.id.editText2);
        EditText loginTextEmailAddress = findViewById(R.id.loginTextEmailAddress);
        EditText loginTextPassword = findViewById(R.id.loginTextPassword);
        FloatingActionButton floatingActionButton = findViewById(R.id.fabHome);
        if(currentUser == null){
            bottomNavigationView.setVisibility(View.INVISIBLE);
            floatingActionButton.setVisibility(View.INVISIBLE);
            buttonLoginSubmit.setVisibility(View.VISIBLE);
            buttonLoginToSignUp.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            loginTextEmailAddress.setVisibility(View.VISIBLE);
            loginTextPassword.setVisibility(View.VISIBLE);
            buttonLogout.setVisibility(View.INVISIBLE);

        } else {
            Log.i(TAG, "onCreate: Username:  " + preferredUserName);
            bottomNavigationView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
            buttonLoginSubmit.setVisibility(View.INVISIBLE);
            buttonLoginToSignUp.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            editText2.setVisibility(View.INVISIBLE);
            loginTextEmailAddress.setVisibility(View.INVISIBLE);
            loginTextPassword.setVisibility(View.INVISIBLE);
            buttonLogout.setVisibility(View.VISIBLE);


            Amplify.Auth.fetchUserAttributes(
                    success ->
                    {
                        for (AuthUserAttribute userInfo : success) {
                            if (userInfo.getKey().getKeyString().equals("preferred_username")) {
                                String usersName = userInfo.getValue();
                                runOnUiThread(() -> {
                                    ((TextView) findViewById(R.id.textView)).setText("Hi "+usersName+"!");
                                });


                            }
                        }
                    },
                    failure -> {
                        Log.i(TAG, "onCreate: Username not found : " + failure);
                    }
            );
        }
    }

    public void logoutButtonSetup(){
        LinearLayout buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> {
            System.out.println("LogoutButton!");
            Log.e(TAG, "onClick: Logout Button!");
            Amplify.Auth.signOut(
                    () -> {
                        Log.i(TAG, "Logout completed: ");
                        Intent goToLoginIntent = new Intent(LoginActivity.this, LoginActivity.class);
                        LoginActivity.this.startActivity(goToLoginIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Logout not completed: " + failure.toString());
                    }
            );
        });
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

        });
    }


    public void setUpButtonToSignUp(){

        LinearLayout buttonToSignUp = findViewById(R.id.buttonLoginToSignUp);

        buttonToSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);


        });

    }

    public void setUpNavBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bnm_developers);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();

                if (id == R.id.bnm_settings) {

                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_profile) {

                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_findTools) {

                    Intent intent = new Intent(LoginActivity.this, FindToolActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_developers) {
                    Intent intent = new Intent(LoginActivity.this,DevActivity.class);
                    startActivity(intent);
                    return true;
                }




                return false;
            }

        });
    }
    public void setUpCreateToolNavigation(){

        FloatingActionButton floatingActionButton = findViewById(R.id.fabHome);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, CreateToolActivity.class);
            startActivity(intent);

        });



    }


}