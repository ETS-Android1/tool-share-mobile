package com.toolsharemobile.myapplication.activity;

import static com.toolsharemobile.myapplication.utility.SortToolList.sortToolList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;
import com.toolsharemobile.myapplication.adapter.ToolListingRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FindToolActivity extends AppCompatActivity {

    private static final String TAG = "MESSAGE";
    public static final String TOOL_ID_TAG = "Tool Id Tag";
    ToolListingRecyclerViewAdapter adapter;
    BottomNavigationView bottomNavigationView;
    List<Tool> toolList = null;
    FusedLocationProviderClient locationProviderClient = null;
    double currentUserLat;
    double currentUserLon;
    Spinner toolTypeSpinner;
    AuthUser authUser;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tool);
        toolList = new ArrayList<>();

        if (Amplify.Auth.getCurrentUser() != null) {
            authUser = Amplify.Auth.getCurrentUser();
            username = authUser.getUsername();
        }


        setUpLocationServices();
        filterToolType();
        filterByDistance();
        resetToolsListing();
        setUpFindToolRecyclerView();
        setUpNavBar();
        setUpCreateToolNavigation();
    }


    public void setUpLocationServices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationProviderClient.getLastLocation().addOnSuccessListener(location ->
                {
                    if (location == null) {
                        Log.e(TAG, "Location callback was null!");
                    }

                    currentUserLat = location.getLatitude();
                    currentUserLon = location.getLongitude();
                    Log.i(TAG, "Our latitude: " + location.getLatitude());
                    Log.i(TAG, "Our longitude: " + location.getLongitude());
                }
        ).addOnCanceledListener(() ->
        {
            Log.e(TAG, "Location request was canceled!");
        })
                .addOnFailureListener(failure ->
                {
                    Log.e(TAG, "Location request failed! Error was: " + failure.getMessage(), failure.getCause());
                })
                .addOnCompleteListener(complete ->
                {
                    Log.e(TAG, "Location request completed!");

                });
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
                        if (!databaseTool.getListedByUser().equals(username))
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

        adapter = new ToolListingRecyclerViewAdapter(toolList, this);
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
                } else if (id == R.id.bnm_developers) {
                    Intent intent = new Intent(FindToolActivity.this,DevActivity.class);
                    startActivity(intent);
                    return true;
                }




                return false;
            }

        });
    }


    public void filterToolType() {
        toolTypeSpinner = findViewById(R.id.spinnerFilterToolType);

        toolTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.preference_category, ToolTypeEnum.values()));

        Button buttonFilterByDistance = findViewById(R.id.buttonFilterByDistance);

        buttonFilterByDistance.setOnClickListener(view -> {

            Amplify.API.query(
                    ModelQuery.list(Tool.class),
                    success -> {
                        Log.i(TAG, "Updated Tools Successfully!");
                        toolList.clear();
                        for (Tool databaseTool : success.getData()) {
                            if (databaseTool.getToolType().equals((ToolTypeEnum) toolTypeSpinner.getSelectedItem()) && !databaseTool.getListedByUser().equals(username))
                                toolList.add(databaseTool);
                        }
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                    },

                    failure -> Log.i(TAG, "failed with this response: ")
            );
        });


    }

    public void resetToolsListing(){
        Button resetFilter = findViewById(R.id.filterResetButton);
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.API.query(
                        ModelQuery.list(Tool.class),
                        success -> {
                            Log.i(TAG, "Updated Tools Successfully!");
                            toolList.clear();
                            for (Tool databaseTool : success.getData()) {
                                if (!databaseTool.getListedByUser().equals(username))
                                    toolList.add(databaseTool);
                            }
                            runOnUiThread(() -> adapter.notifyDataSetChanged());
                        },

                        failure -> Log.i(TAG, "failed with this response: ")
                );
            }
        });
    }

    public void setUpCreateToolNavigation(){

        FloatingActionButton floatingActionButton = findViewById(R.id.fabHome);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(FindToolActivity.this, CreateToolActivity.class);
            startActivity(intent);

        });



    }


    public void filterByDistance() {


        Button buttonFilterByDistance = findViewById(R.id.buttonFilterByDistanceLatLon);


        buttonFilterByDistance.setOnClickListener(view -> {


            for (int i = 0; i < toolList.size(); i++) {

                Tool tool = toolList.get(i);

                double distanceToCalc = latLongDist(currentUserLat, currentUserLon, Double.parseDouble(tool.getLat()), Double.parseDouble(tool.getLon()));
                Tool updateTool = Tool.builder()
                        .toolType(tool.getToolType())
                        .listedByUser(tool.getListedByUser())
                        .lat(tool.getLat())
                        .lon(tool.getLon())
                        .id(tool.getId())
                        .borrowByUser(tool.getBorrowByUser())
                        .openBorrowRequest(tool.getOpenBorrowRequest())
                        .openReturnRequest(tool.getOpenReturnRequest())
                        .isAvailable(tool.getIsAvailable())
                        .distance(distanceToCalc)
                        .build();

                toolList.remove(i);

                toolList.add(i, updateTool);
                Amplify.API.mutate(
                        ModelMutation.update(updateTool),
                        successResponse -> {
                            Log.i(TAG, username + ": Made a Tool successfully!");

                        },
                        failureResponse -> {
                            Log.i(TAG, "failed with this response: " + failureResponse);
                            Snackbar.make(findViewById(R.id.createToolActivity), "Failed to create tool listing!", Snackbar.LENGTH_SHORT).show();

                        }
                );

            }

            sortToolList(toolList);
//            Comparator<Tool> toolComparator = Comparator.comparing(Tool::getDistance);
//            toolList.sort(toolComparator);
            runOnUiThread(() -> adapter.notifyDataSetChanged());


        });


    }


    public static double latLongDist(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (earthRadius * c);
        double distMiles = (0.00062137119224 * dist);

        return distMiles;
    }


}