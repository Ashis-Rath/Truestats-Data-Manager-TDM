package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CovidBed extends AppCompatActivity {
//    ImageView backbtnCoBed;
    TextView backBtnTV;
    String userUID;
    // Below are the TV for Data from Database
    TextView nameOfOrgTVCoHosp, addressOfOrgTVCoHosp, CoHospTotalVentilatorBed, CoHospVacantVentilatorBed, CoHospTotalOxygenBed, CoHospVacantOxygenBed;
    // Below are the TV Click & Update
    TextView editCoHospTotalVentilatorBed, editCoHospVacantVentilatorBed, editCoHospTotalOxygenBed, editCoHospVacantOxygenBed;
    ProgressBar progressBarCovidBed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_bed);

        // Block User Touch Events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Getting Intent Info.
        userUID = getIntent().getStringExtra("uuid"); // Got this Value from HomePage.java

        // Initialising
        nameOfOrgTVCoHosp = findViewById(R.id.nameOfOrgTVCoHosp);
        addressOfOrgTVCoHosp = findViewById(R.id.addressOfOrgTVCoHosp);
        CoHospTotalVentilatorBed = findViewById(R.id.CoHospTotalVentilatorBed);
        CoHospVacantVentilatorBed = findViewById(R.id.CoHospVacantVentilatorBed);
        CoHospTotalOxygenBed = findViewById(R.id.CoHospTotalOxygenBed);
        CoHospVacantOxygenBed = findViewById(R.id.CoHospVacantOxygenBed);
        progressBarCovidBed = findViewById(R.id.progressBarOrgName);
        progressBarCovidBed.setVisibility(View.VISIBLE);

        // Database Stuff
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");
        database.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameOfOrganisation = String.valueOf(snapshot.child("Name").getValue());
                String addressOfOrganisation = String.valueOf(snapshot.child("Address").getValue());
                String totalCovidVentilatorBed = String.valueOf(snapshot.child("Total_ICU_Ventilator_Beds").getValue());
                String vacantCovidVentilatorBed = String.valueOf(snapshot.child("Vacant_ICU_Ventilator_Beds").getValue());
                String totalCovidOxygenBed = String.valueOf(snapshot.child("Total_Oxygen_Beds").getValue());
                String vacantCovidOxygenBed = String.valueOf(snapshot.child("Vacant_Oxygen_Beds").getValue());


                nameOfOrgTVCoHosp.setText(nameOfOrganisation);
                addressOfOrgTVCoHosp.setText(addressOfOrganisation);
                CoHospTotalVentilatorBed.setText(totalCovidVentilatorBed);
                CoHospVacantVentilatorBed.setText(vacantCovidVentilatorBed);
                CoHospTotalOxygenBed.setText(totalCovidOxygenBed);
                CoHospVacantOxygenBed.setText(vacantCovidOxygenBed);



                progressBarCovidBed.setVisibility(View.INVISIBLE);
                // Allow User Touch Events
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Clicking on Back Button
        backBtnTV = findViewById(R.id.backBtnTV);
        backBtnTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Clicking on Total ICU/Ventilator Beds
        editCoHospTotalVentilatorBed = findViewById(R.id.editCoHospTotalVentilatorBed);
        editCoHospTotalVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidBed.this, CovidUpdatetotalVentilatorBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Vacant ICU/Ventilator Beds
        editCoHospVacantVentilatorBed = findViewById(R.id.editCoHospVacantVentilatorBed);
        editCoHospVacantVentilatorBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidBed.this, CovidUpdateVacantVentilatorBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Total Oxygen Beds
        editCoHospTotalOxygenBed = findViewById(R.id.editCoHospTotalOxygenBed);
        editCoHospTotalOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidBed.this, CovidUpdateTotalOxygenBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });

        // Clicking on Vacant Oxygen Beds
        editCoHospVacantOxygenBed = findViewById(R.id.editCoHospVacantOxygenBed);
        editCoHospVacantOxygenBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CovidBed.this, CovidUpdateVacantOxygenBed.class);
                intent.putExtra("uuid", userUID);
                startActivity(intent);
            }
        });
    }
}