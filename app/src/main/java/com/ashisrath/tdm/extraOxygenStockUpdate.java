package com.ashisrath.tdm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class extraOxygenStockUpdate extends AppCompatActivity {
    Spinner spinnerOxygenStatus;
    Button updateBtnOxy, cancelBtnOxy;
    String oxygenStatus, oxyStatus, userOxyID;
    ProgressBar progressBarOxygenUpdate;

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_oxygen_stock_update);

        // Getting Total Values of Bed from Previous Activity
        userOxyID = getIntent().getStringExtra("UUIDforOxy"); // Got this Value from HospitalBedUpdate.java

        // Initialising
        progressBarOxygenUpdate = findViewById(R.id.progressBarOrgName);

        // Database Stuff
        dbref = FirebaseDatabase.getInstance().getReference().child("Oxygen Supplier");

        // Cancel Button
        cancelBtnOxy = findViewById(R.id.cancelHospVacantNormalBed);
        cancelBtnOxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Oxygen Spinner
        spinnerOxygenStatus = findViewById(R.id.spinnerOxygenStatus);
        ArrayAdapter<CharSequence> adapterOxy = ArrayAdapter.createFromResource(this, R.array.OxygenSpinnerUpdate, android.R.layout.simple_spinner_item);
        adapterOxy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOxygenStatus.setAdapter(adapterOxy);
        spinnerOxygenStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oxygenStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Update Button
        updateBtnOxy = findViewById(R.id.updateHospVacantNormalBed);
        updateBtnOxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateOxyData();
            }
        });
    }
    //Spinner State Validation
    private void setSpinnerError(Spinner spinnerOxygenStatus, String error){
        View selectedView = spinnerOxygenStatus.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinnerOxygenStatus.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinnerOxygenStatus.performClick(); // to open the spinner list if error is found.

        }
    }

    private void validateOxyData() {
        oxyStatus = spinnerOxygenStatus.getSelectedItem().toString();

        if (oxyStatus.equals("Select your status.")) {
            setSpinnerError(spinnerOxygenStatus, "Select Status.");
        } else {
            updateStatus();
        }

    }

    private void updateStatus() {
        progressBarOxygenUpdate.setVisibility(View.VISIBLE);

        // Date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());
        // Time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        dbref.child(userOxyID).child("Update_Date").setValue(date);
        dbref.child(userOxyID).child("Update_Time").setValue(time);
        dbref.child(userOxyID).child("Oxygen_Stock_Status").setValue(oxyStatus);

        // Intent put on Off
//        Intent intent = new Intent(extraOxygenStockUpdate.this, OxygenSupplyUpdate.class);
//        startActivity(intent);
        finish();
    }
}