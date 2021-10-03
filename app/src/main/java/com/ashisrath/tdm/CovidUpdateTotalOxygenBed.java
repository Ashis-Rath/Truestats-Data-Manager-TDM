package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CovidUpdateTotalOxygenBed extends AppCompatActivity {
    EditText editCovidHospTotalOxygenBedET;
    Button updateCovidHospTotalOxygenBed, cancelCovidHospTotalOxygenBed;
    String userUID, valueOfTotalCovidOxygenBed;
    ProgressBar progressBarCovidTotalO;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_update_total_oxygen_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editCovidHospTotalOxygenBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarCovidTotalO = findViewById(R.id.progressBarCovidTotalO);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");

        // Cancel Button
        cancelCovidHospTotalOxygenBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelCovidHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateCovidHospTotalOxygenBed = findViewById(R.id.updateHospVacantNormalBed);
        updateCovidHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });


    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfTotalCovidOxygenBed = editCovidHospTotalOxygenBedET.getText().toString();

        if (valueOfTotalCovidOxygenBed.isEmpty()){
            editCovidHospTotalOxygenBedET.setError("Please enter the total number of Oxygen Beds in your COVID Hospital.");
            editCovidHospTotalOxygenBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarCovidTotalO.setVisibility(View.VISIBLE);
        databaseReference.child(userUID).child("Total_Oxygen_Beds").setValue(valueOfTotalCovidOxygenBed);
        finish();
    }
}