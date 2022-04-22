package com.toolsharemobile.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.toolsharemobile.myapplication.R;

public class DevActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

        setUpNavBar();
        setUpCreateToolNavigation();
        linkGithubLinkedInSetup();
    }

    public void linkGithubLinkedInSetup(){

        ImageView linkedInKevin = findViewById(R.id.linkedInKevin);
        linkedInKevin.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/kevin-lamarca/");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        });
        ImageView linkedInJosh = findViewById((R.id.linkedInJosh));
        linkedInJosh.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/joshua-mccluskey/");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        });
        ImageView linkedInShane = findViewById(R.id.linkedInShane);
        linkedInShane.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/shane-roach-908987226/");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        });
        ImageView gitHubKevin = findViewById(R.id.gitHubKevin);
        gitHubKevin.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/KevLaMDev");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        });
        ImageView gitHubJosh = findViewById(R.id.gitHubJosh);
        gitHubJosh.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/joshuamccluskey");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        });
        ImageView gitHubShane = findViewById(R.id.gitHubShane);
        gitHubShane.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/Shane-Patrick-Roach");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
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

                    Intent intent = new Intent(DevActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_profile) {

                    Intent intent = new Intent(DevActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_findTools) {

                    Intent intent = new Intent(DevActivity.this, FindToolActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_developers) {
                    Intent intent = new Intent(DevActivity.this,DevActivity.class);
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
            Intent intent = new Intent(DevActivity.this, CreateToolActivity.class);
            startActivity(intent);

        });



    }
}