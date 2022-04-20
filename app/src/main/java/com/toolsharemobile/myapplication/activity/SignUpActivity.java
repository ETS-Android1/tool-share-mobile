package com.toolsharemobile.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.toolsharemobile.myapplication.R;

public class SignUpActivity extends AppCompatActivity {

    String TAG = "SIGN UP ACTIVITY";
    FusedLocationProviderClient locationProvider = null;
    String lat = null;
    String lon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationProvider = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationProvider.flushLocations();
        signupButtonSetUp();

    }
    public void signupButtonSetUp(){
        LinearLayout buttonSignUpToVerify = findViewById(R.id.buttonSignUpToVerify);
        buttonSignUpToVerify.setOnClickListener(view -> {
            System.out.println("Signup signup Button!");
            Log.e(TAG, "onClick: Signup signup Button!");

            String email = ((EditText) findViewById(R.id.signupTextEmailAddress)).getText().toString();
            String password = ((EditText) findViewById(R.id.signupTextPassword)).getText().toString();
            String confirmPassword = ((EditText) findViewById(R.id.editTextTextPasswordConfirm)).getText().toString();
            String username = ((EditText) findViewById(R.id.signupTextUserame)).getText().toString();
            lat = "90.0000";
            lon = "45.0000";

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)

            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG, "Application does not have access to either ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION!");
                return;
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            locationProvider.getLastLocation().addOnSuccessListener(location ->  // "location" here could be null if no one else has request a location prior!
                            // Try running Google Maps first if you have a null callback here!
                    {
                        if (location == null)
                        {
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


            Amplify.Auth.signUp(
                    email,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), email)
                            .userAttribute(AuthUserAttributeKey.preferredUsername(), username)
                            .userAttribute(AuthUserAttributeKey.custom("lat"),lat)
                            .userAttribute(AuthUserAttributeKey.custom("lon"), lon)
                            .build(),
                    good -> {
                        Log.i(TAG, "Signup completed: " + good);
                        Intent goToVerifyIntent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
                        goToVerifyIntent.putExtra(TAG, email);
                        SignUpActivity.this.startActivity(goToVerifyIntent);
                    },
                    bad -> {
                        Log.i(TAG, "Signup not completed: " + bad);
                        runOnUiThread(()->
                        {
                            Toast.makeText(SignUpActivity.this, "Signup not successful!", Toast.LENGTH_SHORT);
                        });

                    }
            );



        });
    }

}