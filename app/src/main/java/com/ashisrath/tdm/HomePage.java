package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ImageView popUpMenu, imageView7, imageView5, imageView6, viewProfileIcon, aboutUs, manual;
    TextView logedinName, accountTypeTV;
    String accountType, User_Email;
    ProgressBar progressBarNew;
    String user_id;




    // Firebase
    private FirebaseAuth Auth;
    // Firebase

    //*****************************Keep User Logged In*************************************************************
    // SharedPreferences 2nd Trial
    SharedPreferences sharedPreferences;
    // SharedPreferences 2nd Trial
    //*****************************Keep User Logged In*************************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Block User Touch Events
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        progressBarNew = findViewById(R.id.progressBarOrgName);
        progressBarNew.setVisibility(View.VISIBLE);

        // Initialising Firebase Auth
        Auth = FirebaseAuth.getInstance();
        // Initialising Firebase Auth

        //*****************************Keep User Logged In*************************************************************
        // SharedPreferences 2nd Trial
        //Get that instance saved in the previous activity
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        // SharedPreferences 2nd Trial
        //*****************************Keep User Logged In*************************************************************




        logedinName = findViewById(R.id.logedinName);
        FirebaseUser AuthUser = Auth.getCurrentUser();
        assert AuthUser != null;
        User_Email = AuthUser.getEmail();
        logedinName.setText(User_Email);


        //Account Type TextView
        accountTypeTV = findViewById(R.id.accountTypeTV);
        user_id = Auth.getUid();
        assert user_id != null;





        //***************************************************************************************************
        //Checking Account Type : Hospital
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospital");
        databaseReference.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String accountTypeHospital = String.valueOf(snapshot.child("Category").getValue());
                if (accountTypeHospital.equals("Hospital")){
                    accountTypeTV.setText(accountTypeHospital);
                    accountType = accountTypeTV.getText().toString();
                    progressBarNew.setVisibility(View.INVISIBLE);
                    // Allow User Touch Events
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
//                else {
//                    accountTypeTV.setText("Oxygen Supplier");
//                }

//                // 09 June
//                progressBarNew.setVisibility(View.INVISIBLE);
//                // Allow User Touch Events
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Checking Account Type : Oxygen Supplier
        DatabaseReference mdbRef = FirebaseDatabase.getInstance().getReference().child("Oxygen Supplier");
        mdbRef.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String accountTypeOxygen = String.valueOf(snapshot.child("Category").getValue());
                if (accountTypeOxygen.equals("Oxygen Supplier")){
                    accountTypeTV.setText(accountTypeOxygen);
                    accountType = accountTypeTV.getText().toString();
                    progressBarNew.setVisibility(View.INVISIBLE);
                    // Allow User Touch Events
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Checking Account Type : COVID Hospital
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("COVID Hospital");
        dbRef.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String accountTypeCovidHospital = String.valueOf(snapshot.child("Category").getValue());
                if (accountTypeCovidHospital.equals("COVID Hospital")){
                    accountTypeTV.setText(accountTypeCovidHospital);
                    accountType = accountTypeTV.getText().toString();
                    progressBarNew.setVisibility(View.INVISIBLE);
                    // Allow User Touch Events
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //***************************************************************************************************


        // Clicking on Hospital Beds
        imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals("Hospital")) {
                    Intent intent = new Intent(HomePage.this, HospitalBedUpdate.class);
                    intent.putExtra("uuid", user_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomePage.this, "Access Denied: Considering your Service Type, you don't have access to this feature.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clicking on Oxygen Supply
        imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ((accountTypeTV.getText().toString()).equals("Oxygen Supplier")) {
//                    Intent intent = new Intent(HomePage.this, OxygenSupplyUpdate.class);
//                    intent.putExtra("uuidForOxy", user_id);
//                    startActivity(intent);
//                }
                if (accountType.equals("Oxygen Supplier")) {
                    Intent intent = new Intent(HomePage.this, OxygenSupplyUpdate.class);
                    intent.putExtra("uuidForOxy", user_id);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(HomePage.this, "Access Denied: Considering your Service Type, you don't have access to this feature.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Clicking on COVID Hospital
        imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals("COVID Hospital")) {
                    Intent intent = new Intent(HomePage.this, CovidBed.class);
                    intent.putExtra("uuid", user_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomePage.this, "Access Denied: Considering your Service Type, you don't have access to this feature.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Clicking on About Us
        aboutUs = findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AboutUs.class);
                startActivity(intent);
            }
        });

        // Clicking on Manual
        manual = findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Manual.class);
                startActivity(intent);
            }
        });


        // 17 May 2021


        // Clicking on View Profile
        viewProfileIcon = findViewById(R.id.viewProfileIcon);
        viewProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile();
            }
        });



        // Popup Menu
        popUpMenu = findViewById(R.id.popUpMenuProfile);
        popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Three-Dot Menu
                PopupMenu popup = new PopupMenu(HomePage.this, v);
                popup.setOnMenuItemClickListener(HomePage.this);
                popup.inflate(R.menu.main_menu);
                popup.show();
                // Three-Dot Menu

            }

        });
        // Popup Menu



    }

    private void viewProfile() {
        String serviceTypeProfile = accountTypeTV.getText().toString();
        Intent intent = new Intent(HomePage.this, ViewProfileActivity.class);
                intent.putExtra("uuidProfileView", user_id);
                intent.putExtra("ServiceCode", serviceTypeProfile);
                intent.putExtra("U_Email", User_Email);
        startActivity(intent);
    }





    // Three-Dot Menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.help:
//                Toast.makeText(this, "You clicked on Help.", Toast.LENGTH_SHORT).show();
////                return true;
//                break;
//            case R.id.aboutus:
//                Toast.makeText(this, "You clicked on About us.", Toast.LENGTH_SHORT).show();
////                return true;
//                break;
            case R.id.signoutmenu:
                FirebaseAuth.getInstance().signOut();

                //*****************************Keep User Logged In*************************************************************
                // SharedPreferences 2nd Trial
                //Resetting value to 0 so autologin is disabled
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("key", 0);
                editor.apply();
                // SharedPreferences 2nd Trial
                //*****************************Keep User Logged In*************************************************************

                Intent intent = new Intent(HomePage.this, SigninActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
//                return false;
                break;
        }
        return true;
    }
    // Three-Dot Menu

    // Back Button Closes the App
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();

    // super.onBackPressed();
    // Not calling **super**, disables back button in current screen.
    }

}