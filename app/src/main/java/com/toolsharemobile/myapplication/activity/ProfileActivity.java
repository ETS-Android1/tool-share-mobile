package com.toolsharemobile.myapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

//    FusedLocationProviderClient locationProvider = null;
//    String lat = null;
//    String lon = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        locationProvider = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//        locationProvider.flushLocations();
        toolListLended = new ArrayList<>();
        toolListBorrowed = new ArrayList<>();

        setUpLendToolRecyclerView();
        setUpBorrowToolRecyclerView();
        setUpCreateToolNavigation();
//        setupSetLocationButton();
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

//    public void setupSetLocationButton(){
//        Button setLocationButton = findViewById(R.id.locationButton);
//        setLocationButton.setOnClickListener(view -> {
//
//            lat = "90.0000";
//            lon = "45.0000";
//
//            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
//
//            {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                Log.e(TAG, "Application does not have access to either ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION!");
//                return;
//            }
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            locationProvider.getLastLocation().addOnSuccessListener(location ->  // "location" here could be null if no one else has request a location prior!
//                            // Try running Google Maps first if you have a null callback here!
//                    {
//                        if (location == null)
//                        {
//                            Log.e(TAG, "Location callback was null!");
//                        }
//                        lat = Double.toString(location.getLatitude());
//                        lon = Double.toString(location.getLongitude());
//                        Log.i(TAG, "Our latitude: " + location.getLatitude());
//                        Log.i(TAG, "Our longitude: " + location.getLongitude());
//                    }
//            ).addOnCanceledListener(() ->
//            {
//                Log.e(TAG, "Location request was canceled!");
//            })
//                    .addOnFailureListener(failure ->
//                    {
//                        Log.e(TAG, "Location request failed! Error was: " + failure.getMessage(), failure.getCause());
//                    })
//                    .addOnCompleteListener(complete ->
//                    {
//                        Log.e(TAG, "Location request completed!");
//
//                    });
//
//
//
//
//        });
//    }





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