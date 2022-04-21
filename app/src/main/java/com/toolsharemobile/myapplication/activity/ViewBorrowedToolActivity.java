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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.google.android.material.snackbar.Snackbar;
import com.toolsharemobile.myapplication.R;
import com.toolsharemobile.myapplication.adapter.BorrowToolRecyclerViewAdapter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ViewBorrowedToolActivity extends AppCompatActivity {

    private static final String TAG = "VIEW BORROWED TOOL";
    private CompletableFuture<Tool> toolCompletableFuture = null;
    private Tool toolToEdit;
    AuthUser authUser = null;
    String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrowed_tool);
        toolCompletableFuture = new CompletableFuture<>();

        getCurrentUserLocation();
        setUpUIelements();
        setUpReturnRequestButton();
    }

    private void getCurrentUserLocation() {



    }


    private void setUpUIelements() {
        ImageView mapImage = findViewById(R.id.viewBorrowedToolMapImage);
        TextView toolName = findViewById(R.id.viewBorrowedToolNameTextView);
       // TextView toolOwner = findViewById(R.id.viewToolListingOwnerTextView);
        ImageView toolIcon = findViewById(R.id.viewBorrowedToolImageIcon);


        Intent callingIntent = getIntent();
        String toolId = null;

        if (callingIntent != null){
            toolId = callingIntent.getStringExtra(BorrowToolRecyclerViewAdapter.TAG);
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

//            if (toolToEdit.getListedByUser() != null) {
//                toolOwner.setText(toolToEdit.getListedByUser());
//            }

        });


    }



    private void setUpReturnRequestButton() {
        Button borrowToolButton = findViewById(R.id.returnToolSubmitButton);

        borrowToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Amplify.Auth.getCurrentUser() != null) {
                    authUser = Amplify.Auth.getCurrentUser();
                    username = authUser.getUsername();
                }


                Tool toolToSave = Tool.builder()
                        .toolType(toolToEdit.getToolType())
                        .listedByUser(toolToEdit.getListedByUser())
                        .lat(toolToEdit.getLat())
                        .lon(toolToEdit.getLon())
                        .id(toolToEdit.getId())
                        .openBorrowRequest(false)
                        .openReturnRequest(true)
                        .isAvailable(false)
                        .borrowByUser(toolToEdit.getBorrowByUser())
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(toolToSave),
                        successResponse -> {
                            Log.i(TAG, "Created a tool return request successfully!");

                            Intent goToHomeActivity = new Intent(ViewBorrowedToolActivity.this, ProfileActivity.class);
                            startActivity(goToHomeActivity);
                        },
                        failureResponse -> {
                            Log.i(TAG, "Failed to make a tool return request: "+ failureResponse);
                            Snackbar.make(findViewById(R.id.viewToolActivity), "tool return request failed!", Snackbar.LENGTH_SHORT).show();
                        }
                );

            }
        });
    }

}