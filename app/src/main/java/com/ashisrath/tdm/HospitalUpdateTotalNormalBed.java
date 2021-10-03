package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalUpdateTotalNormalBed extends AppCompatActivity {
    EditText editHospTotalNormalBedET;
    Button updateHospTotalNormalBed, cancelHospTotalNormalBed;
    String userUID, valueOfTotalNormalBed;
    ProgressBar progressBarBedTotalNormal;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_total_normal_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospTotalNormalBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedTotalNormal = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospTotalNormalBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospTotalNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Update Button
        updateHospTotalNormalBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospTotalNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfTotalNormalBed = editHospTotalNormalBedET.getText().toString();

        if (valueOfTotalNormalBed.isEmpty()){
            editHospTotalNormalBedET.setError("Please enter the total number of Normal Beds in your Hospital.");
            editHospTotalNormalBedET.requestFocus();
        } else {
            updateBeds();
        }

    }

    private void updateBeds() {
        progressBarBedTotalNormal.setVisibility(View.VISIBLE);
        databaseReference.child(userUID).child("Total_Normal_Beds").setValue(valueOfTotalNormalBed);
        finish();
    }
}