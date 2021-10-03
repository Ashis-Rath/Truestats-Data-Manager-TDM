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

public class HospitalUpdateVacantVentilatorBed extends AppCompatActivity {
    EditText editHospVacantVentilatorBedET;
    Button updateHospVacantVentilatorBed, cancelHospVacantVentilatorBed;
    String userUID, valueOfVacantVentilatorBed;
    ProgressBar progressBarBedVacantVenti;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update_vacant_ventilator_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editHospVacantVentilatorBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarBedVacantVenti = findViewById(R.id.progressBarBedVacantNormal);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");

        // Cancel Button
        cancelHospVacantVentilatorBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelHospVacantVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Update Button
        updateHospVacantVentilatorBed = findViewById(R.id.updateHospVacantNormalBed);
        updateHospVacantVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfVacantVentilatorBed = editHospVacantVentilatorBedET.getText().toString();

        if (valueOfVacantVentilatorBed.isEmpty()){
            editHospVacantVentilatorBedET.setError("Please enter the vacant number of ICU/Ventilator Beds in your Hospital.");
            editHospVacantVentilatorBedET.requestFocus();
        } else {
            updateBeds();
        }

    }

    private void updateBeds() {
        progressBarBedVacantVenti.setVisibility(View.VISIBLE);

        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        databaseReference.child(userUID).child("Vacant_ICU_Ventilator_Beds").setValue(valueOfVacantVentilatorBed);
        databaseReference.child(userUID).child("Update_Date").setValue(date);
        databaseReference.child(userUID).child("Update_Time").setValue(time);
        finish();
    }
}