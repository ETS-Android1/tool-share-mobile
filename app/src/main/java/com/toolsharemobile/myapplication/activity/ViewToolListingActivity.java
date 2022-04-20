package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ViewToolListingActivity extends AppCompatActivity {

    private static final String TAG = "VIEW TOOL";
    private CompletableFuture<Tool> toolCompletableFuture = null;
    private Tool toolToEdit;
    AuthUser authUser = null;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tool_listing);
        toolCompletableFuture = new CompletableFuture<>();



        setUpUIelements();
        setUpBorrowButton();
    }

    private void setUpBorrowButton() {
        Button borrowToolButton = findViewById(R.id.borrowToolSubmitButton);
        borrowToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();


                    Amplify.Auth.fetchUserAttributes(
                            success ->
                            {
                                Log.i(TAG, "Fetch user attributes succeeded for username: " + username);

                                for (AuthUserAttribute userAttribute : success) {
                                    if (userAttribute.getKey().getKeyString().equals("preferred_username")) {
                                        username = userAttribute.getValue();
                                    }
                                }
                            },
                            failure ->
                            {
                                Log.i(TAG, "Fetch user attributes failed: " + failure.toString());
                            }
                    );
                } else {
                    username = "Jimbo";
                }

                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .location(toolToEdit.getLocation())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(false)
                        .isAvailable(false)
                        .borrowByUser(username)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Edited a Task successfully!");

                            Intent goToHomeActivity = new Intent(ViewToolListingActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to edit task with this response: "+ failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Tool not borrowed!", Snackbar.LENGTH_SHORT).show();
                        }
                );

            }
        });
    }

    private void setUpUIelements() {
        ImageView mapImage = findViewById(R.id.viewToolListingMapImage);
        TextView toolName = findViewById(R.id.viewToolListingNameTextView);
        TextView toolOwner = findViewById(R.id.viewToolListingOwnerTextView);
        ImageView toolIcon = findViewById(R.id.viewToolListingImageIcon);


        Intent callingIntent = getIntent();
        String toolId = null;

        if (callingIntent != null){
            toolId = callingIntent.getStringExtra(FindToolActivity.TOOL_ID_TAG);
        }

        String toolId2 = toolId;

        Amplify.API.query(
                ModelQuery.list(Tool.class),
                success ->
                {
                    Log.i(TAG, "Read tool successfully!");

                    for (Tool databaseTool : success.getData()) {
                        if (databaseTool.getId().equals(toolId2)) {
                            toolCompletableFuture.complete(databaseTool);
                        }
                    }

                    runOnUiThread(() ->
                    {
                        // Update UI elements
                    });
                },
                failure -> Log.i(TAG, "Did not read tool successfully!")
        );


        try {
            toolToEdit = toolCompletableFuture.get();
        } catch (InterruptedException ie) {
            Log.e(TAG, "InterruptedException while getting tool");
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            Log.e(TAG, "ExecutionException while getting tool");
        }

        runOnUiThread(()->
        {
            toolName.setText(toolToEdit.getToolType().toString());

            if (toolToEdit.getListedByUser() != null) {
                toolOwner.setText(toolToEdit.getListedByUser());
            }

        });


    }
}