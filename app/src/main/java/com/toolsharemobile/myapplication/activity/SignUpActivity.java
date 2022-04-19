package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.toolsharemobile.myapplication.R;

public class SignUpActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpNavBar();
    }



    public void setUpNavBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bnm_settings);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();

                if(id == R.id.bnm_home) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);

                    return true;
                }
                else if (id == R.id.bnm_settings) {

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_profile) {

                    Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_findTools) {

                    Intent intent = new Intent(SignUpActivity.this, FindToolActivity.class);
                    startActivity(intent);
                    return true;
                }


                return false;
            }

        });
    }
}