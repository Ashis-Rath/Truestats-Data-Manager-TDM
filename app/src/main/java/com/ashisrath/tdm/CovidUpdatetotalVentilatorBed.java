package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CovidUpdatetotalVentilatorBed extends AppCompatActivity {
    EditText editCovidHospTotalVentilatorBedET;
    Button updateCovidHospTotalVentilatorBed, cancelCovidHospTotalVentilatorBed;
    String userUID, valueOfTotalCovidVentilatorBed;
    ProgressBar progressBarCovidTV;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_updatetotal_ventilator_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editCovidHospTotalVentilatorBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarCovidTV = findViewById(R.id.progressBarCovidTV);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");

        // Cancel Button
        cancelCovidHospTotalVentilatorBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelCovidHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Update Button
        updateCovidHospTotalVentilatorBed = findViewById(R.id.updateHospVacantNormalBed);
        updateCovidHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfTotalCovidVentilatorBed = editCovidHospTotalVentilatorBedET.getText().toString();

        if (valueOfTotalCovidVentilatorBed.isEmpty()){
            editCovidHospTotalVentilatorBedET.setError("Please enter the total number of ICU/Ventilator Beds in your COVID Hospital.");
            editCovidHospTotalVentilatorBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarCovidTV.setVisibility(View.VISIBLE);
        databaseReference.child(userUID).child("Total_ICU_Ventilator_Beds").setValue(valueOfTotalCovidVentilatorBed);
        finish();
    }
}