package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HospitalUpdateVacantNormalBed extends AppCompatActivity {
    EditText editHospVacantNormalBedET;
    Button updateHospVacantNormalBed, cancelHospVacantNormalBed;
    String userUID, valueOfVacantNormalBed;
    ProgressBar progressBarBedVacantNormal;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_vacant_normal_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospVacantNormalBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedVacantNormal = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospVacantNormalBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospVacantNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Update Button
        updateHospVacantNormalBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospVacantNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });


    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfVacantNormalBed = editHospVacantNormalBedET.getText().toString();

        if (valueOfVacantNormalBed.isEmpty()){
            editHospVacantNormalBedET.setError("Please enter the vacant number of Normal Beds in your Hospital.");
            editHospVacantNormalBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarBedVacantNormal.setVisibility(View.VISIBLE);
        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        databaseReference.child(userUID).child("Vacant_Normal_Beds").setValue(valueOfVacantNormalBed);
        databaseReference.child(userUID).child("Update_Date").setValue(date);
        databaseReference.child(userUID).child("Update_Time").setValue(time);
        finish();
    }
}