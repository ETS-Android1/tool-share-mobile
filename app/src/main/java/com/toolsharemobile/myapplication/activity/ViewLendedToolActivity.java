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
}