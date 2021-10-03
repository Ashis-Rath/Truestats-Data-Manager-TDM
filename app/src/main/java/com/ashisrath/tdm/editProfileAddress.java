package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfileAddress extends AppCompatActivity {
    String userUID, accountCategory, newAddressOfOrg;
    EditText editAddressET;
    Button updateBtnEditAddress, cancelBtnEditAddress;
    DatabaseReference profileDb;
    ProgressBar progressBarOrgAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_address);

        // Getting Intent Information from previous Activity
        userUID = getIntent().getStringExtra("UUID"); // Got this Value from ViewProfileActivity.java
        accountCategory = getIntent().getStringExtra("serviceProvider"); // Got this Value from ViewProfileActivity.java

        // Initialising EditText & Button
        editAddressET = findViewById(R.id.editHospVacantNormalBedET);
        updateBtnEditAddress = findViewById(R.id.updateHospVacantNormalBed);
        cancelBtnEditAddress = findViewById(R.id.cancelHospVacantNormalBed);
        progressBarOrgAddress = findViewById(R.id.progressBarOrgAddress);

        // Database Stuff
        profileDb = FirebaseDatabase.getInstance().getReference().child(accountCategory);

        // Cancel Button
        cancelBtnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update Button
        updateBtnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDataEditAddress();
            }
        });

    }

    private void ValidateDataEditAddress() {
        // Getting the Input as Strings from the Edit Texts Above
        newAddressOfOrg = editAddressET.getText().toString();

        if (newAddressOfOrg.isEmpty()){
            editAddressET.setError("Please enter the Address of your Organisation.");
            editAddressET.requestFocus();
        } else {
            updateAddressofOrg();
        }
    }

    private void updateAddressofOrg() {
        progressBarOrgAddress.setVisibility(View.VISIBLE);
        profileDb.child(userUID).child("Address").setValue(newAddressOfOrg);
        finish();
    }

}