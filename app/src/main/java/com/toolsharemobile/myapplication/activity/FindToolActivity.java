package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.toolsharemobile.myapplication.R;

import java.util.ArrayList;



public class FindToolActivity extends AppCompatActivity {

    //ToolListingRecyclerViewAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tool);

        setUpFindToolRecyclerView();
        setUpNavBar();
    }


    private void setUpFindToolRecyclerView() {

        RecyclerView toolListRecyclerView = findViewById(R.id.rvFindTool);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        toolListRecyclerView.setLayoutManager(layoutManager);


//        Tool crowBar = new Tool("CrowBar");
//        Tool wrench = new Tool("Wrench");
//        Tool vaccum = new Tool("Vaccum");
//        Tool ladder = new Tool("Ladder");


        //List<Tool> toolList = new ArrayList<>();

//        toolList.add(crowBar);
//        toolList.add(wrench);
//        toolList.add(vaccum);
//        toolList.add(ladder);


//        adapter = new ToolListingRecyclerViewAdapter(toolList);
//        toolListRecyclerView.setAdapter(adapter);

    }


    public void setUpNavBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bnm_findTools);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();

                if (id == R.id.bnm_settings) {

                    Intent intent = new Intent(FindToolActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_profile) {

                    Intent intent = new Intent(FindToolActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.bnm_findTools) {

                    Intent intent = new Intent(FindToolActivity.this, FindToolActivity.class);
                    startActivity(intent);
                    return true;
                }


                return false;
            }

        });
    }
}