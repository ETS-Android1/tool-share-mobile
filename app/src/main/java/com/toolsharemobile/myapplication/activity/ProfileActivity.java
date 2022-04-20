package com.toolsharemobile.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.toolsharemobile.myapplication.R;
import com.toolsharemobile.myapplication.adapter.ToolListingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE";
    BottomNavigationView bottomNavigationView;
    ToolListingRecyclerViewAdapter adapterLendTool;
    ToolListingRecyclerViewAdapter adapterBorrowTool;
    List<Tool> toolListLended = null;
    List<Tool> toolListBorrowed = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolListLended = new ArrayList<>();
        toolListBorrowed = new ArrayList<>();

        setUpLendToolRecyclerView();
        setUpBorrowToolRecyclerView();
        setUpCreateToolNavigation();
        setUpNavBar();


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Tool.class),
                success -> {
                    Log.i(TAG, "Updated Lended Tools Successfully!");
                    toolListLended.clear();
                    for (Tool databaseTool : success.getData()) {
                        toolListLended.add(databaseTool);
                    }
                    runOnUiThread(() -> adapterLendTool.notifyDataSetChanged());
                },

                failure -> Log.i(TAG, "failed with this response: ")
        );

        Amplify.API.query(
                ModelQuery.list(Tool.class),
                success -> {
                    Log.i(TAG, "Updated Lended Tools Successfully!");
                    toolListBorrowed.clear();
                    for (Tool databaseTool : success.getData()) {
                        toolListBorrowed.add(databaseTool);
                    }
                    runOnUiThread(() -> adapterBorrowTool.notifyDataSetChanged());
                },

                failure -> Log.i(TAG, "failed with this response: ")
        );


    }


    private void setUpLendToolRecyclerView() {

        RecyclerView toolListRecyclerView = findViewById(R.id.recyclerViewToolsLended);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        toolListRecyclerView.setLayoutManager(layoutManager);

        adapterLendTool = new ToolListingRecyclerViewAdapter(toolListLended);
        toolListRecyclerView.setAdapter(adapterLendTool);


    }

    private void setUpBorrowToolRecyclerView() {

        RecyclerView toolListRecyclerView = findViewById(R.id.recyclerViewToolsBorrowed);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        toolListRecyclerView.setLayoutManager(layoutManager);

        adapterBorrowTool = new ToolListingRecyclerViewAdapter(toolListBorrowed);
        toolListRecyclerView.setAdapter(adapterBorrowTool);

    }


    public void setUpNavBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bnm_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();


                if (id == R.id.bnm_settings) {

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_profile) {

                    Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.bnm_findTools) {

                    Intent intent = new Intent(ProfileActivity.this, FindToolActivity.class);
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
            Intent intent = new Intent(ProfileActivity.this, CreateToolActivity.class);
            startActivity(intent);

        });



    }
}