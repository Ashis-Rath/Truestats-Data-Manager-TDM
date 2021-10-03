package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfileLocationURL extends AppCompatActivity {
    EditText editUrlET;
    Button updateBtnEditUrl, cancelBtnEditUrl;
    String userUID, accountCategory, locationUrlOfOrg;
    DatabaseReference profileDb;
    ProgressBar progressBarOrgLocationUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_location_url);

        // Getting Intent Information from previous Activity
        userUID = getIntent().getStringExtra("UUID"); // Got this Value from ViewProfileActivity.java
        accountCategory = getIntent().getStringExtra("serviceProvider"); // Got this Value from ViewProfileActivity.java

        // Initialising EditText & Button
        editUrlET = findViewById(R.id.editHospVacantNormalBedET);
        updateBtnEditUrl = findViewById(R.id.updateHospVacantNormalBed);
        cancelBtnEditUrl = findViewById(R.id.cancelHospVacantNormalBed);
        progressBarOrgLocationUrl = findViewById(R.id.progressBarOrgLocationUrl);

        // Database Stuff
        profileDb = FirebaseDatabase.getInstance().getReference().child(accountCategory);

        // Cancel Button
        cancelBtnEditUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateBtnEditUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataEditLocationURL();
            }
        });
    }

    private void ValidateDataEditLocationURL() {
        // Getting the Input as Strings from the Edit Texts Above
        locationUrlOfOrg = editUrlET.getText().toString();

        if (locationUrlOfOrg.isEmpty()){
            editUrlET.setError("Please enter the Google Map URL of your Organisation.");
            editUrlET.requestFocus();
        } else {
            updateLocationUrlofOrg();
        }
    }

    private void updateLocationUrlofOrg() {
        progressBarOrgLocationUrl.setVisibility(View.VISIBLE);
        profileDb.child(userUID).child("Location_URL").setValue(locationUrlOfOrg);
        finish();
    }
}