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

public class CovidUpdateVacantOxygenBed extends AppCompatActivity {
    EditText editCovidHospVacantOxygenBedET;
    Button updateCovidHospVacantOxygenBed, cancelCovidHospVacantOxygenBed;
    String userUID, valueOfVacantCovidOxygenBed;
    ProgressBar progressBarCovidVO;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_update_vacant_oxygen_bed);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        editCovidHospVacantOxygenBedET = findViewById(R.id.editHospVacantNormalBedET);
        progressBarCovidVO = findViewById(R.id.progressBarCovidVO);

        // Database Stuff
        databaseReference = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");

        // Cancel Button
        cancelCovidHospVacantOxygenBed = findViewById(R.id.cancelHospVacantNormalBed);
        cancelCovidHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateCovidHospVacantOxygenBed = findViewById(R.id.updateHospVacantNormalBed);
        updateCovidHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        // Getting the Input as Strings from the Edit Texts Above
        valueOfVacantCovidOxygenBed = editCovidHospVacantOxygenBedET.getText().toString();

        if (valueOfVacantCovidOxygenBed.isEmpty()){
            editCovidHospVacantOxygenBedET.setError("Please enter the vacant number of Oxygen Beds in your COVID Hospital.");
            editCovidHospVacantOxygenBedET.requestFocus();
        } else {
            updateBeds();
        }
    }

    private void updateBeds() {
        progressBarCovidVO.setVisibility(View.VISIBLE);

        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        databaseReference.child(userUID).child("Vacant_Oxygen_Beds").setValue(valueOfVacantCovidOxygenBed);
        databaseReference.child(userUID).child("Update_Date").setValue(date);
        databaseReference.child(userUID).child("Update_Time").setValue(time);
        finish();
    }
}