package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfileName extends AppCompatActivity {
    String userUID, accountCategory, newNameOfOrg;
    EditText editNameET;
    Button cancelBtnEditName, updateBtnEditName;
    DatabaseReference profileDb;
    ProgressBar progressBarOrgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_name);

        // Getting Intent Information from previous Activity
        userUID = getIntent().getStringExtra("UUID"); // Got this Value from ViewProfileActivity.java
        accountCategory = getIntent().getStringExtra("serviceProvider"); // Got this Value from ViewProfileActivity.java

        // Initialising EditText & Button
        editNameET = findViewById(R.id.editHospVacantNormalBedET);
        updateBtnEditName = findViewById(R.id.updateHospVacantNormalBed);
        cancelBtnEditName = findViewById(R.id.cancelHospVacantNormalBed);
        progressBarOrgName = findViewById(R.id.progressBarOrgName);

        // Database Stuff
        profileDb = FirebaseDatabase.getInstance().getReference().child(accountCategory);

        // Cancel Button
        cancelBtnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateBtnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataEditName();
            }
        });



    }

    private void ValidateDataEditName() {
        // Getting the Input as Strings from the Edit Texts Above
        newNameOfOrg = editNameET.getText().toString();

        if (newNameOfOrg.isEmpty()){
            editNameET.setError("Please enter the Name of your Organisation.");
            editNameET.requestFocus();
        } else {
            updateNameofOrg();
        }
    }

    private void updateNameofOrg() {
        progressBarOrgName.setVisibility(View.VISIBLE);
        profileDb.child(userUID).child("Name").setValue(newNameOfOrg);
        finish();
    }
}