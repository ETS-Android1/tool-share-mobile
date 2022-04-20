package com.toolsharemobile.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.toolsharemobile.myapplication.R;

public class ViewToolListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tool_listing);

        setUpBorrowButton();
    }

    private void setUpBorrowButton() {
        Button borrowToolButton = findViewById(R.id.borrowToolSubmitButton);
        borrowToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setUpUIelements() {
        ImageView mapImage = findViewById(R.id.viewToolListingMapImage);
        TextView toolName = findViewById(R.id.viewToolListingNameTextView);
        ImageView toolIcon = findViewById(R.id.viewToolListingImageIcon);
    }
}