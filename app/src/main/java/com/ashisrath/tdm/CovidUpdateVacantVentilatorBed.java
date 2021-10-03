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

public class CovidUpdateVacantVentilatorBed extends AppCompatActivity {
    EditText editCovidHospTotalVentilatorBedET1;
    Button updateCovidHospTotalVentilatorBed1, cancelCovidHospTotalVentilatorBed1;
    String userUID, valueOfVacantCovidVentilatorBed;
    ProgressBar progressBarCovidVV;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_update_vacant_ventilator_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editCovidHospTotalVentilatorBedET1 = findViewById(R.id.editHospVacantNormalBedET);
        progressBarCovidVV = findViewById(R.id.progressBarOrgName);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");

        // Cancel Button
        cancelCovidHospTotalVentilatorBed1 = findViewById(R.id.cancelHospVacantNormalBed);
        cancelCovidHospTotalVentilatorBed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateCovidHospTotalVentilatorBed1 = findViewById(R.id.updateHospVacantNormalBed);
        updateCovidHospTotalVentilatorBed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfVacantCovidVentilatorBed = editCovidHospTotalVentilatorBedET1.getText().toString();

        if (valueOfVacantCovidVentilatorBed.isEmpty()){
            editCovidHospTotalVentilatorBedET1.setError("Please enter the vacant number of ICU/Ventilator Beds in your COVID Hospital.");
            editCovidHospTotalVentilatorBedET1.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {

        progressBarCovidVV.setVisibility(View.VISIBLE);

        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        databaseReference.child(userUID).child("Vacant_ICU_Ventilator_Beds").setValue(valueOfVacantCovidVentilatorBed);
        databaseReference.child(userUID).child("Update_Date").setValue(date);
        databaseReference.child(userUID).child("Update_Time").setValue(time);
        finish();
    }
}