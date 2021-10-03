package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OxygenSupplyUpdate extends AppCompatActivity {
    ImageView backbtnOxygenUpdate;
    TextView nameOfOrgTVOxygen, addressOfOrgTVOxygen, oxygenAvailabilityDatabaseValue;
    Button editOxygenAvailabilityBtn;
    String userUidOxy;
    ProgressBar progressBarOxygenUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen_supply_update);

        // Block User Touch Events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Initialising TextViews, ProgressBar & Button
        nameOfOrgTVOxygen = findViewById(R.id.nameOfOrgTVCoHosp);
        addressOfOrgTVOxygen = findViewById(R.id.addressOfOrgTVCoHosp);
        backbtnOxygenUpdate = findViewById(R.id.backbtnCoBed);
        editOxygenAvailabilityBtn = findViewById(R.id.editOxygenAvailabilityBtn);
        oxygenAvailabilityDatabaseValue = findViewById(R.id.CoHospTotalVentilatorBed);

        // Progress Bar Visibility
        progressBarOxygenUpdate = findViewById(R.id.progressBarOrgName);
        progressBarOxygenUpdate.setVisibility(View.VISIBLE);

        // Database Stuff
        userUidOxy = getIntent().getStringExtra("uuidForOxy"); // Got this Value from HomePage.java
        DatabaseReference databaseOxy = FirebaseDatabase.getInstance().getReference().child("Oxygen Supplier");
        databaseOxy.child(userUidOxy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameOfOrganisationOxy = String.valueOf(snapshot.child("Name").getValue());
                String addressOfOrganisationOxy = String.valueOf(snapshot.child("Address").getValue());
                String oxygenStatus = String.valueOf(snapshot.child("Oxygen_Stock_Status").getValue());
                nameOfOrgTVOxygen.setText(nameOfOrganisationOxy);
                addressOfOrgTVOxygen.setText(addressOfOrganisationOxy);
                oxygenAvailabilityDatabaseValue.setText(oxygenStatus);

                // Progress Bar Visibility
                progressBarOxygenUpdate.setVisibility(View.INVISIBLE);
                // Allow User Touch Events
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Back Button
        backbtnOxygenUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Edit Availability Button
        editOxygenAvailabilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OxygenSupplyUpdate.this, extraOxygenStockUpdate.class);
                intent.putExtra("UUIDforOxy", userUidOxy);
                startActivity(intent);
            }
        });

    }
}