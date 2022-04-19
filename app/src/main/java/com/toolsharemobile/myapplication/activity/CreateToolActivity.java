package com.toolsharemobile.myapplication.activity;

import static com.toolsharemobile.myapplication.ToolShareAmplifyApplication.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;

public class CreateToolActivity extends AppCompatActivity {

    private static final String TAG = "TOOL";
    Spinner toolTypeSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tool);


        setUpSpinners();


        LinearLayout addTaskButton = findViewById(R.id.buttonAddToolSubmit);


        addTaskButton.setOnClickListener(v -> {


            Tool newTool = Tool.builder()
                    .toolType((ToolTypeEnum)toolTypeSpinner.getSelectedItem())
                    .listedByUser("Bob")
                    .location("Bob Location")
                    .isAvailable(true)
                    .openReturnRequest(false)
                    .openBorrowRequest(false)
                    .build();


            Amplify.API.mutate(
                    ModelMutation.create(newTool),
                    successResponse -> {
                        Log.i(TAG, "Made a Tool successfully!");
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