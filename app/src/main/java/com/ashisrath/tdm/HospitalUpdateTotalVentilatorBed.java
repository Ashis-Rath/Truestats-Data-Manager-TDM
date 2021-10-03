package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalUpdateTotalVentilatorBed extends AppCompatActivity {
    EditText editHospTotalVentilatorBedET;
    Button updateHospTotalVentilatorBed, cancelHospTotalVentilatorBed;
    String userUID, valueOfTotalVentilatorBed;
    ProgressBar progressBarBedTotalVenti;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_total_ventilator_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospTotalVentilatorBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedTotalVenti = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospTotalVentilatorBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Update Button
        updateHospTotalVentilatorBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfTotalVentilatorBed = editHospTotalVentilatorBedET.getText().toString();

        if (valueOfTotalVentilatorBed.isEmpty()){
            editHospTotalVentilatorBedET.setError("Please enter the total number of ICU/Ventilator Beds in your Hospital.");
            editHospTotalVentilatorBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarBedTotalVenti.setVisibility(View.VISIBLE);
        databaseReference.child(userUID).child("Total_ICU_Ventilator_Beds").setValue(valueOfTotalVentilatorBed);
        finish();
    }
}