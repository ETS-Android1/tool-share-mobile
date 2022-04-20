package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;

public class CreateToolActivity extends AppCompatActivity {

    String TAG = " CREATE TOOL ACTIVITY";
    Spinner toolTypeSpinner = null;
    AuthUser authUser;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tool);


        setUpCreateTool();
        setUpSpinners();

    }


    public void setUpCreateTool(){

        if(Amplify.Auth.getCurrentUser() != null) {
            authUser = Amplify.Auth.getCurrentUser();
            username = authUser.getUsername();
        }







        LinearLayout addTaskButton = findViewById(R.id.buttonAddToolSubmit);


        addTaskButton.setOnClickListener(v -> {


            Tool newTool = Tool.builder()
                    .toolType((ToolTypeEnum)toolTypeSpinner.getSelectedItem())
                    .listedByUser(username)
                    .location("Bob Location")
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