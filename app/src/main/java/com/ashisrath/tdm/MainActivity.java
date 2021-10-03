package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button loginbtnmainactivity;
    TextView signupbtnmainactivity;


    SharedPreferences preferences;

//    // Run One Time - SharedPreferences
//    SharedPreferences sharedPreferences;
//    Boolean firstTime;
//    // Run One Time - SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if application is opened for the first time
        preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){
            // If application was opened for the first time
            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }



//        // Run One Time
//        //*******************************************************************************************
//        // Initialising SharedPreferences
//        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        // Initialising SharedPreferences
//
//        // Checking the Value of Boolean firstTime (If its really First Time)
//        firstTime = sharedPreferences.getBoolean("firstTime", true);
//        if (firstTime){
//            // Method to Perform action after 60000ms or 1 min
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Run One Time SharedPreferences
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    firstTime = false;
//                    editor.putBoolean("firstTime", firstTime);
//                    editor.apply();
//                    // Run One Time SharedPreferences
//
//                    Intent intent = new Intent(MainActivity.this, SigninActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 60000);
//            // Method to Perform action after 120000ms or 2 mins
//        } else {
//            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        // Checking the Value of Boolean firstTime (If its really First Time)
//
//
//        //*******************************************************************************************
//        // Run One Time


        loginbtnmainactivity = findViewById(R.id.signinbtnmainactivity);
        loginbtnmainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignin = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intentSignin);
            }
        });

        signupbtnmainactivity = findViewById(R.id.signupbtnmainactivity);
        signupbtnmainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignup = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intentSignup);
            }
        });
    }
}