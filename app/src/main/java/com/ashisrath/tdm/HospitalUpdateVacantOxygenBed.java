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

public class HospitalUpdateVacantOxygenBed extends AppCompatActivity {
    EditText editHospVacantOxygenBedET;
    Button updateHospVacantOxygenBed, cancelHospVacantOxygenBed;
    String userUID, valueOfVacantOxygenBed;
    ProgressBar progressBarBedVacantOxi;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_vacant_oxygen_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospVacantOxygenBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedVacantOxi = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospVacantOxygenBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Update Button
        updateHospVacantOxygenBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfVacantOxygenBed = editHospVacantOxygenBedET.getText().toString();

        if (valueOfVacantOxygenBed.isEmpty()){
            editHospVacantOxygenBedET.setError("Please enter the vacant number of Oxygen Beds in your Hospital.");
            editHospVacantOxygenBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarBedVacantOxi.setVisibility(View.VISIBLE);

        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        databaseReference.child(userUID).child("Vacant_Oxygen_Beds").setValue(valueOfVacantOxygenBed);
        databaseReference.child(userUID).child("Update_Date").setValue(date);
        databaseReference.child(userUID).child("Update_Time").setValue(time);
        finish();
    }
}