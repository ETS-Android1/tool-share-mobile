package com.toolsharemobile.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;

public class CreateToolActivity extends AppCompatActivity {

    String TAG = " CREATE TOOL ACTIVITY";
    Spinner toolTypeSpinner = null;
    FusedLocationProviderClient locationProviderClient = null;
    String lat = null;
    String lon = null;

    AuthUser authUser;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tool);

        lat = "90.0000";
        lon = "45.0000";




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

                    lat = Double.toString(location.getLatitude());
                    lon = Double.toString(location.getLongitude());
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


        setUpCreateTool();
        setUpSpinners();


    }


    public void setUpCreateTool() {

        if (Amplify.Auth.getCurrentUser() != null) {
            authUser = Amplify.Auth.getCurrentUser();
            username = authUser.getUsername();
        }

        LinearLayout addTaskButton = findViewById(R.id.buttonAddToolSubmit);
        addTaskButton.setOnClickListener(v -> {


            Tool newTool = Tool.builder()
                    .toolType((ToolTypeEnum) toolTypeSpinner.getSelectedItem())

                    .listedByUser(username)
                    .location(lat + "," + lon)
                    .isAvailable(true)
                    .openReturnRequest(false)
                    .openBorrowRequest(false)
                    .build();


            Amplify.API.mutate(
                    ModelMutation.create(newTool),
                    successResponse -> {
                        Log.i(TAG, username + ": Made a Tool successfully!");
                        Intent intent = new Intent(CreateToolActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    },
                    failureResponse -> {
                        Log.i(TAG, "failed with this response: " + failureResponse);
                        Snackbar.make(findViewById(R.id.createToolActivity), "Failed to create tool listing!", Snackbar.LENGTH_SHORT).show();

                    }
            );

        });
    }


    private void setUpSpinners() {

        toolTypeSpinner = (Spinner) findViewById(R.id.spinnerAddToolType);

        toolTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.preference_category, ToolTypeEnum.values()));
    }


}