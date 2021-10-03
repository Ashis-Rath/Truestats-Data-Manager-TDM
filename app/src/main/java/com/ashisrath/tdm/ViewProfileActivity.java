package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfileActivity extends AppCompatActivity {
    private String UniqueID, AccountType;
    LinearLayout editName, editPrivateNumber, editAddress, editPublicNumber, editLocation;
    ImageView backbtnViewProfile;
    Button databaseProfileBackBtn, cohospProfileDeleteBtn;
    ProgressBar progressBarViewProfile;
    TextView databaseProfileUUID, databaseProfileName, databaseProfilePrivateEmail, databaseProfilePrivateNumber, databaseProfileAddress,
            databaseProfileState, databaseProfileCity, databaseProfileServiceType, databaseProfileLocationURL,
            properNameClick, properAddressClick, databaseProfilePublicNumber, properPublicNumberClick, properLocationURLClick, properPrivateNumberClick;

    String User_Email;

    DatabaseReference dbRef;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // Block User Touch Events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //*****************************Keep User Logged In*************************************************************
        // SharedPreferences 2nd Trial
        //Get that instance saved in the previous activity
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        // SharedPreferences 2nd Trial
        //*****************************Keep User Logged In*************************************************************

        // Progress Bar
        progressBarViewProfile = findViewById(R.id.progressBarViewProfile);
        progressBarViewProfile.setVisibility(View.VISIBLE);

        // Intent Information
        UniqueID = getIntent().getStringExtra("uuidProfileView"); // Got this Value from HomePage.java
        AccountType = getIntent().getStringExtra("ServiceCode"); // Got this Value from HomePage.java
        User_Email = getIntent().getStringExtra("U_Email"); // Got this Value from HomePage.java

        // Database Stuff
        dbRef = FirebaseDatabase.getInstance().getReference().child(AccountType);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialising Button, ImageView & TextView
        editName = findViewById(R.id.editName);
        editPrivateNumber = findViewById(R.id.editPrivateNumber);
        editAddress = findViewById(R.id.editAddress);
        editPublicNumber = findViewById(R.id.editPublicNumber);
        editLocation = findViewById(R.id.editLocation);

        databaseProfileBackBtn = findViewById(R.id.cohospProfileBackBtn);
        backbtnViewProfile = findViewById(R.id.backbtnCoBed);
        databaseProfileName = findViewById(R.id.databaseCovidHospitalName);
        databaseProfilePrivateEmail = findViewById(R.id.databaseProfilePrivateEmail);
        databaseProfilePrivateNumber = findViewById(R.id.databaseProfilePrivateNumber);
        databaseProfileAddress = findViewById(R.id.databaseProfileAddress);
        databaseProfileState = findViewById(R.id.databaseProfileState);
        databaseProfileCity = findViewById(R.id.databaseProfileCity);
        databaseProfilePublicNumber = findViewById(R.id.databaseProfilePublicNumber);
        databaseProfileServiceType = findViewById(R.id.databaseProfileServiceType);
        databaseProfileLocationURL = findViewById(R.id.databaseProfileLocationURL);
        databaseProfileUUID = findViewById(R.id.databaseProfileUUID);

        // Database Stuff
        DatabaseReference profileDbReference = FirebaseDatabase.getInstance().getReference().child(AccountType);
        profileDbReference.child(UniqueID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameOfOrganisationProfile = String.valueOf(snapshot.child("Name").getValue());
//                String privateEmailOfOrganisationProfile = String.valueOf(snapshot.child("E_mail").getValue());
                String privatePhoneNumberOfOrganisationProfile = String.valueOf(snapshot.child("Phone_number").getValue());
                String addressOfOrganisationProfile = String.valueOf(snapshot.child("Address").getValue());
                String stateOfOrganisationProfile = String.valueOf(snapshot.child("State").getValue());
                String cityOfOrganisationProfile = String.valueOf(snapshot.child("City").getValue());
                String publicNumberOfOrganisationProfile = String.valueOf(snapshot.child("Public_Phone_number").getValue());
                // Replace with AccountType
//                String serviceOfOrganisationProfile = String.valueOf(snapshot.child("Category").getValue());
                String locationUrlOfOrganisationProfile = String.valueOf(snapshot.child("Location_URL").getValue());
                String keyOfOrganisationProfile = String.valueOf(snapshot.child("Key").getValue());

                databaseProfileName.setText(nameOfOrganisationProfile);
                databaseProfilePrivateEmail.setText(User_Email);
                databaseProfilePrivateNumber.setText(privatePhoneNumberOfOrganisationProfile);
                databaseProfileAddress.setText(addressOfOrganisationProfile);
                databaseProfileState.setText(stateOfOrganisationProfile);
                databaseProfileCity.setText(cityOfOrganisationProfile);
                databaseProfilePublicNumber.setText(publicNumberOfOrganisationProfile);
                // Replace with AccountType
//                databaseProfileServiceType.setText(serviceOfOrganisationProfile);
                databaseProfileServiceType.setText(AccountType);
                databaseProfileLocationURL.setText(locationUrlOfOrganisationProfile);
                databaseProfileUUID.setText(keyOfOrganisationProfile);

                // Progress Bar
                progressBarViewProfile.setVisibility(View.INVISIBLE);
                // Allow User Touch Events
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        // Back Arrow Button
        backbtnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Back Button
        databaseProfileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Delete Button
        cohospProfileDeleteBtn = findViewById(R.id.cohospProfileDeleteBtn);
        cohospProfileDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ViewProfileActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in the complete removal of your data from the system which won't let you access this app in future.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser();
//                        assert firebaseUser != null;
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ViewProfileActivity.this, "Your Account has now been Deleted!!", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();

                                    //*****************************Keep User Logged In*************************************************************
                                    // SharedPreferences 2nd Trial
                                    //Resetting value to 0 so autologin is disabled
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("key", 0);
                                    editor.apply();
                                    // SharedPreferences 2nd Trial
                                    //*****************************Keep User Logged In*************************************************************

                                    Intent intent = new Intent(ViewProfileActivity.this, SigninActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ViewProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        // Click on Edit Name
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, editProfileName.class);
                intent.putExtra("UUID", UniqueID);
                intent.putExtra("serviceProvider", AccountType);
                startActivity(intent);
            }
        });

        // Click on Edit Address Icon
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, editProfileAddress.class);
                intent.putExtra("UUID", UniqueID);
                intent.putExtra("serviceProvider", AccountType);
                startActivity(intent);

            }
        });

        // Click on Edit Public Number Icon
        editPublicNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, editProfilePublicNumber.class);
                intent.putExtra("UUID", UniqueID);
                intent.putExtra("serviceProvider", AccountType);
                startActivity(intent);

            }
        });

        // Click on Edit Location URL Icon
        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, editProfileLocationURL.class);
                intent.putExtra("UUID", UniqueID);
                intent.putExtra("serviceProvider", AccountType);
                startActivity(intent);

            }
        });

        // Click on Edit Private Number
        editPrivateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, editProfilePrivateNumber.class);
                intent.putExtra("UUID", UniqueID);
                intent.putExtra("serviceProvider", AccountType);
                startActivity(intent);

            }
        });




    }

    private void deleteUser() {
        dbRef.child(UniqueID).removeValue();
    }
}