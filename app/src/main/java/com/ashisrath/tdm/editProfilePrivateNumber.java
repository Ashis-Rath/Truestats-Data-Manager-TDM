package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfilePrivateNumber extends AppCompatActivity {
    EditText editPrivateNumberET;
    Button updateBtnEditPrivateNumber, cancelBtnEditPrivateNumber;
    String userUID, accountCategory, privateNumberOfOrg;
    DatabaseReference profileDb;
    ProgressBar progressBarOrgPrivateNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_private_number);

        // Getting Intent Information from previous Activity
        userUID = getIntent().getStringExtra("UUID"); // Got this Value from ViewProfileActivity.java
        accountCategory = getIntent().getStringExtra("serviceProvider"); // Got this Value from ViewProfileActivity.java

        // Initialising EditText & Button
        editPrivateNumberET = findViewById(R.id.editHospVacantNormalBedET);
        updateBtnEditPrivateNumber = findViewById(R.id.updateHospVacantNormalBed);
        cancelBtnEditPrivateNumber = findViewById(R.id.cancelHospVacantNormalBed);
        progressBarOrgPrivateNumber = findViewById(R.id.progressBarOrgPrivateNumber);

        // Database Stuff
        profileDb = FirebaseDatabase.getInstance().getReference().child(accountCategory);

        // Cancel Button
        cancelBtnEditPrivateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateBtnEditPrivateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataEditPublicNumber();
            }
        });
    }

    private void ValidateDataEditPublicNumber() {
        // Getting the Input as Strings from the Edit Texts Above
        privateNumberOfOrg = editPrivateNumberET.getText().toString();

        if (privateNumberOfOrg.isEmpty()){
            editPrivateNumberET.setError("Please enter the Private contact number of your Organisation.");
            editPrivateNumberET.requestFocus();
        } else {
            updatePrivateNumberofOrg();
        }
    }

    private void updatePrivateNumberofOrg() {
        progressBarOrgPrivateNumber.setVisibility(View.VISIBLE);
        profileDb.child(userUID).child("Phone_number").setValue(privateNumberOfOrg);
        finish();
    }
}