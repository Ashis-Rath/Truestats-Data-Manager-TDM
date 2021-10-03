package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalUpdateTotalOxygenBed extends AppCompatActivity {
    EditText editHospTotalOxygenBedET;
    Button updateHospTotalOxygenBed, cancelHospTotalOxygenBed;
    String userUID, valueOfTotalOxygenBed;
    ProgressBar progressBarBedTotalOxi;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_total_oxygen_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospTotalOxygenBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedTotalOxi = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospTotalOxygenBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Update Button
        updateHospTotalOxygenBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfTotalOxygenBed = editHospTotalOxygenBedET.getText().toString();

        if (valueOfTotalOxygenBed.isEmpty()){
            editHospTotalOxygenBedET.setError("Please enter the total number of Oxygen Beds in your Hospital.");
            editHospTotalOxygenBedET.requestFocus();
        } else {
            updateBeds();
        }

    }

    private void updateBeds() {
        progressBarBedTotalOxi.setVisibility(View.VISIBLE);
        databaseReference.child(userUID).child("Total_Oxygen_Beds").setValue(valueOfTotalOxygenBed);
        finish();
    }
}