package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalBedUpdate extends AppCompatActivity {
    ImageView backbtnHospUpdate;
    TextView nameOfOrgTV, addressOfOrgTV, ventilatorBedDatabaseValue, oxygenBedDatabaseValue, normalBedDatabaseValue, vacantVentilatorBedDatabaseValue, vacantOxygenBedDatabaseValue, vacantNormalBedDatabaseValue;
    TextView editCoHospTotalVentilatorBed, editCoHospVacantVentilatorBed, editCoHospVacantOxygenBed, editCoHospTotalOxygenBed,
        editCoHospTotalNormalBed, editCoHospVacantNormalBed;
    private String userUID;
    ProgressBar progressBarBedStatistics;
    String totalVentilatorBedFetch, totalOxygenBedFetch, totalNormalBedFetch, vacantVentilatorBedFetch, vacantOxygenBedFetch, vacantNormalBedFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_bed_update);

        // Block User Touch Events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //Initialising TextView & Button
        nameOfOrgTV = findViewById(R.id.nameOfOrgTVCoHosp);
        addressOfOrgTV = findViewById(R.id.addressOfOrgTVCoHosp);
        ventilatorBedDatabaseValue = findViewById(R.id.CoHospTotalVentilatorBed);
        oxygenBedDatabaseValue = findViewById(R.id.CoHospTotalOxygenBed);
        normalBedDatabaseValue = findViewById(R.id.normalBedDatabaseValue);
        vacantVentilatorBedDatabaseValue = findViewById(R.id.CoHospVacantVentilatorBed);
        vacantOxygenBedDatabaseValue = findViewById(R.id.CoHospVacantOxygenBed);
        vacantNormalBedDatabaseValue = findViewById(R.id.vacantNormalBedDatabaseValue);

        editCoHospTotalVentilatorBed = findViewById(R.id.editCoHospTotalVentilatorBed);
        editCoHospVacantVentilatorBed = findViewById(R.id.editCoHospVacantVentilatorBed);
        editCoHospTotalOxygenBed = findViewById(R.id.editCoHospTotalOxygenBed);
        editCoHospVacantOxygenBed = findViewById(R.id.editCoHospVacantOxygenBed);


        // Progress Bar Visibility
        progressBarBedStatistics = findViewById(R.id.progressBarBedVacantNormal);
        progressBarBedStatistics.setVisibility(View.VISIBLE);



        // Getting Name & Address details from Database
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Hospital");
        database.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameOfOrganisation = String.valueOf(snapshot.child("Name").getValue());
                String addressOfOrganisation = String.valueOf(snapshot.child("Address").getValue());
                totalVentilatorBedFetch = String.valueOf(snapshot.child("Total_ICU_Ventilator_Beds").getValue());
                totalOxygenBedFetch = String.valueOf(snapshot.child("Total_Oxygen_Beds").getValue());
                totalNormalBedFetch = String.valueOf(snapshot.child("Total_Normal_Beds").getValue());
                vacantVentilatorBedFetch = String.valueOf(snapshot.child("Vacant_ICU_Ventilator_Beds").getValue());
                vacantOxygenBedFetch = String.valueOf(snapshot.child("Vacant_Oxygen_Beds").getValue());
                vacantNormalBedFetch = String.valueOf(snapshot.child("Vacant_Normal_Beds").getValue());


                nameOfOrgTV.setText(nameOfOrganisation);
                addressOfOrgTV.setText(addressOfOrganisation);
                ventilatorBedDatabaseValue.setText(totalVentilatorBedFetch);
                oxygenBedDatabaseValue.setText(totalOxygenBedFetch);
                normalBedDatabaseValue.setText(totalNormalBedFetch);
                vacantVentilatorBedDatabaseValue.setText(vacantVentilatorBedFetch);
                vacantOxygenBedDatabaseValue.setText(vacantOxygenBedFetch);
                vacantNormalBedDatabaseValue.setText(vacantNormalBedFetch);



                // Progress Bar Visibility
                progressBarBedStatistics.setVisibility(View.INVISIBLE);
                // Allow User Touch Events
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Back Button
        backbtnHospUpdate = findViewById(R.id.backbtnCoBed);
        backbtnHospUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Clicking on Edit Total ICU/Ventilator Bed
        editCoHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateTotalVentilatorBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Edit Total ICU/Ventilator Bed
        editCoHospVacantVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateVacantVentilatorBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Edit Total Oxygen Bed
        editCoHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateTotalOxygenBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Edit Vacant Oxygen Bed
        editCoHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateVacantOxygenBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Edit Total Normal Bed
        editCoHospTotalNormalBed = findViewById(R.id.editCoHospTotalNormalBed);
        editCoHospTotalNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateTotalNormalBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Edit Vacant Normal Bed
        editCoHospVacantNormalBed = findViewById(R.id.editCoHospVacantNormalBed);
        editCoHospVacantNormalBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalBedUpdate.this, HospitalUpdateVacantNormalBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });




    }

}