package com.toolsharemobile.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
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
            LinearLayout borrowToolButton = findViewById(R.id.requestSubmitButton);
            LinearLayout pendingButton = findViewById(R.id.pendingButton);
            pendingButton.setVisibility(View.INVISIBLE);
            borrowToolButton.setVisibility(View.VISIBLE);
            if (toolToEdit.getOpenBorrowRequest()){
               borrowToolButton.setVisibility(View.INVISIBLE);
               pendingButton.setVisibility(View.VISIBLE);
            }

            if (toolToEdit.getToolType().equals(ToolTypeEnum.CROWBAR)) toolIcon.setImageResource(R.drawable.crowbar);
            if (toolToEdit.getToolType().equals(ToolTypeEnum.JIGSAW)) toolIcon.setImageResource(R.drawable.jigsaw);
            if (toolToEdit.getToolType().equals(ToolTypeEnum.DRILL)) toolIcon.setImageResource(R.drawable.drill);
            if (toolToEdit.getToolType().equals(ToolTypeEnum.SLEDGEHAMMER)) toolIcon.setImageResource(R.drawable.sledgehammer);
            if (toolToEdit.getToolType().equals(ToolTypeEnum.CIRCULARSAW)) toolIcon.setImageResource(R.drawable.circular_saw);

        });


    }



    private void setUpReturnRequestButton() {
        LinearLayout borrowToolButton = findViewById(R.id.requestSubmitButton);

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