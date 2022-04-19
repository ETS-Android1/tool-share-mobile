package com.toolsharemobile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class ToolShareAmplifyApplication extends Application {
    public static final String TAG = "TOOL SHARE MOBILE APPLICATION";
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException ae) {
            Log.e(TAG, "Could not initialize Amplify", ae);
        }
    }
}