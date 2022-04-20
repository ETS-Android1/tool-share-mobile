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
import com.google.android.material.navigation.NavigationBarView;
import com.toolsharemobile.myapplication.R;
import com.toolsharemobile.myapplication.adapter.ToolListingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FindToolActivity extends AppCompatActivity {

    private static final String TAG = "MESSAGE";
    ToolListingRecyclerViewAdapter adapter;
    BottomNavigationView bottomNavigationView;
    List<Tool> toolList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tool);
        toolList = new ArrayList<>();

        setUpFindToolRecyclerView();
        setUpNavBar();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Tool.class),
                success -> {
                    Log.i(TAG, "Updated Tools Successfully!");
                    toolList.clear();
                    for (Tool databaseTool : success.getData()) {
                        toolList.add(databaseTool);
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                },

                failure -> Log.i(TAG, "failed with this response: ")
        );
    }


    private void setUpFindToolRecyclerView() {

        RecyclerView toolListRecyclerView = findViewById(R.id.rvFindTool);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        toolListRecyclerView.setLayoutManager(layoutManager);

        adapter = new ToolListingRecyclerViewAdapter(toolList);
        toolListRecyclerView.setAdapter(adapter);

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