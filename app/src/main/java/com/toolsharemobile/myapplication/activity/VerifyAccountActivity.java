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
import com.amplifyframework.core.Amplify;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.toolsharemobile.myapplication.R;

public class VerifyAccountActivity extends AppCompatActivity {

    String TAG = "VerifyAccountActivity";
    static String TAG_VERIFY =  "VerifyAccountTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        verifyButtonSetUp();
    }

    public void verifyButtonSetUp(){
        LinearLayout buttonVerifyAccountSubmit = findViewById(R.id.buttonVerifyAccountSubmit);
//        Intent gettingIntent = getIntent();
//        String email = gettingIntent.getStringExtra(SignupActivity.TAG_SIGNUP_EMAIL);
        EditText verifyTextEmailAddress = findViewById(R.id.verifyTextEmailAddress);
        buttonVerifyAccountSubmit.setOnClickListener(view -> {

            String userEmail = verifyTextEmailAddress.getText().toString();
            String verification = ((EditText) findViewById(R.id.verifyTextPassword)).getText().toString();

            Amplify.Auth.confirmSignUp(userEmail,
                    verification,
                    good -> {
                        System.out.println("Verify Button!");
                        Log.i(TAG, "onClick: Verify Button!" + good);
                        Intent goToMainIntent = new Intent(VerifyAccountActivity.this, LoginActivity.class);
                        VerifyAccountActivity.this.startActivity(goToMainIntent);
                    },
                    bad -> {
                        Log.i(TAG, "onClick: Verify Button!" + bad);
                        runOnUiThread(()->
                        {
                            Toast.makeText(VerifyAccountActivity.this, "Verification not successful!", Toast.LENGTH_SHORT);
                        });
                    });



        });
    }
}