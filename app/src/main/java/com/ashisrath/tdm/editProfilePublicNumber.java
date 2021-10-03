package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfilePublicNumber extends AppCompatActivity {
    EditText editPublicNumberET;
    Button updateBtnEditPublicNumber, cancelBtnEditPublicNumber;
    String userUID, accountCategory, publicNumberOfOrg;
    DatabaseReference profileDb;
    ProgressBar progressBarOrgPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_public_number);

        // Getting Intent Information from previous Activity
        userUID = getIntent().getStringExtra("UUID"); // Got this Value from ViewProfileActivity.java
        accountCategory = getIntent().getStringExtra("serviceProvider"); // Got this Value from ViewProfileActivity.java

        // Initialising EditText & Button
        editPublicNumberET = findViewById(R.id.editHospVacantNormalBedET);
        updateBtnEditPublicNumber = findViewById(R.id.updateHospVacantNormalBed);
        cancelBtnEditPublicNumber = findViewById(R.id.cancelHospVacantNormalBed);
        progressBarOrgPhoneNumber = findViewById(R.id.progressBarOrgPhoneNumber);

        // Database Stuff
        profileDb = FirebaseDatabase.getInstance().getReference().child(accountCategory);

        // Cancel Button
        cancelBtnEditPublicNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateBtnEditPublicNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataEditPublicNumber();
            }
        });
    }

    private void ValidateDataEditPublicNumber() {
        // Getting the Input as Strings from the Edit Texts Above
        publicNumberOfOrg = editPublicNumberET.getText().toString();

        if (publicNumberOfOrg.isEmpty()){
            editPublicNumberET.setError("Please enter the Public contact number of your Organisation.");
            editPublicNumberET.requestFocus();
        } else {
            updateNumberofOrg();
        }
    }

    private void updateNumberofOrg() {
        progressBarOrgPhoneNumber.setVisibility(View.VISIBLE);
        profileDb.child(userUID).child("Public_Phone_number").setValue(publicNumberOfOrg);
        finish();
    }
}