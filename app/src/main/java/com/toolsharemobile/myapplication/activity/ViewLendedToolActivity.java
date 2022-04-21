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
import com.toolsharemobile.myapplication.adapter.BorrowToolRecyclerViewAdapter;
import com.toolsharemobile.myapplication.adapter.LendToolRecyclerViewAdapter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ViewLendedToolActivity extends AppCompatActivity {


    private static final String TAG = "VIEW LENDED TOOL";
    private CompletableFuture<Tool> toolCompletableFuture = null;
    private Tool toolToEdit;
    AuthUser authUser = null;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lended_tool);
        toolCompletableFuture = new CompletableFuture<>();

        setUpUIelements();
        setUpRemoveToolListingButton();
        setUpConfirmBorrowButton();
        setUpDenyBorrowButton();
        setUpConfirmReturnButton();
        setUpContestReturnButton();
    }


    private void setUpUIelements() {

        TextView toolName = findViewById(R.id.viewOwnedToolNameTextView);
        //ImageView toolIcon = findViewById(R.id.viewBorrowedToolImageIcon);


        Intent callingIntent = getIntent();
        String toolId = null;

        if (callingIntent != null) {
            toolId = callingIntent.getStringExtra(LendToolRecyclerViewAdapter.TAG);
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

        runOnUiThread(() ->
        {
            toolName.setText(toolToEdit.getToolType().toString());

//            if (toolToEdit.getListedByUser() != null) {
//                toolOwner.setText(toolToEdit.getListedByUser());
//            }

        });
    }



    public void setUpRemoveToolListingButton(){

        Button deleteToolButton = findViewById(R.id.deleteToolSubmitButton);
        deleteToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                }


                Amplify.API.mutate(
                        ModelMutation.delete(toolToEdit),
                        successResponse -> {
                            Log.i(TAG, "Removed a Tool successfully!");

                            Intent goToHomeActivity = new Intent(ViewLendedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to edit task with this response: "+ failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Tool not removed!", Snackbar.LENGTH_SHORT).show();
                        }
                );

            }
        });
    }


    public void setUpConfirmBorrowButton(){

        Button confirmBorrowRequest = findViewById(R.id.confirmButton);
        confirmBorrowRequest.setVisibility(View.INVISIBLE);
        if (toolToEdit.getOpenBorrowRequest()) {
            confirmBorrowRequest.setVisibility(View.VISIBLE);
        }
        confirmBorrowRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                } else {
                    username = "Jimbo";
                }

                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .lat(toolToEdit.getLat())
                        .lon(toolToEdit.getLon())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(false)
                        .isAvailable(false)
                        .borrowByUser(toolToEdit.getBorrowByUser())
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Confirmed Tool borrow request successfully!");

                            Intent goToHomeActivity = new Intent(ViewLendedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to confirm tool borrow request with this response: " + failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Failed to confirm tool borrow request!", Snackbar.LENGTH_SHORT).show();
                        }
                );
            }
        });
    }

    public void setUpDenyBorrowButton() {
        Button denyBorrowButton = findViewById(R.id.denyButton);
        denyBorrowButton.setVisibility(View.INVISIBLE);
        if (toolToEdit.getOpenBorrowRequest()) {
            denyBorrowButton.setVisibility(View.VISIBLE);
        }
        denyBorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                } else {
                    username = "Jimbo";
                }

                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .lat(toolToEdit.getLat())
                        .lon(toolToEdit.getLon())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(false)
                        .isAvailable(true)
                        .borrowByUser(null)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Denied Tool borrow request successfully!");

                            Intent goToHomeActivity = new Intent(ViewLendedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to deny tool borrow request with this response: " + failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Failed to deny tool borrow request!", Snackbar.LENGTH_SHORT).show();
                        }
                );
            }
        });
    }

    public void setUpConfirmReturnButton() {
        Button confirmReturnButton = findViewById(R.id.returnConfirmButton);
        confirmReturnButton.setVisibility(View.INVISIBLE);
        if (toolToEdit.getOpenReturnRequest()) {
            confirmReturnButton.setVisibility(View.VISIBLE);
        }
        confirmReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                } else {
                    username = "Jimbo";
                }

                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .lat(toolToEdit.getLat())
                        .lon(toolToEdit.getLon())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(false)
                        .isAvailable(true)
                        .borrowByUser(null)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Confirmed Tool return request successfully!");

                            Intent goToHomeActivity = new Intent(ViewLendedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to confirm tool return request with this response: " + failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Failed to confirm tool return request!", Snackbar.LENGTH_SHORT).show();
                        }
                );
            }
        });
    }

    public void setUpContestReturnButton() {
        Button contestReturnButton = findViewById(R.id.returnButtonDeny);
        contestReturnButton.setVisibility(View.INVISIBLE);
        if (toolToEdit.getOpenReturnRequest()) {
            contestReturnButton.setVisibility(View.VISIBLE);
        }
        contestReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                } else {
                    username = "Jimbo";
                }

                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .lat(toolToEdit.getLat())
                        .lon(toolToEdit.getLon())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(false)
                        .isAvailable(false)
                        .borrowByUser(toolToEdit.getBorrowByUser())
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Contested Tool return request successfully!");

                            Intent goToHomeActivity = new Intent(ViewLendedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to Contest tool return request with this response: " + failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "Failed to Contest tool return request!", Snackbar.LENGTH_SHORT).show();
                        }
                );
            }
        });
    }


}