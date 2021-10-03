package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
    private ImageView backbtnsignin;
    private TextView signupbtnsigninactivity, btnforgotpassword;
    private Button signinbtnmainactivity;
    private EditText loginEmailSigninActivity, loginPasswordSigninActivity;
    ProgressBar progressBar2;

    private String logEmail, logPassword;

    // Firebase
    private FirebaseAuth Auth;
    // Firebase

    // SharedPreferences 2nd Trial
    SharedPreferences sharedpreferences;
    int autoSave;
    // SharedPreferences 2nd Trial


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //findViewByID: Email & Password, Sign in Button, Progress Bar
        loginEmailSigninActivity = findViewById(R.id.loginEmailSigninActivity);
        loginPasswordSigninActivity = findViewById(R.id.loginPasswordSigninActivity);
        signinbtnmainactivity = findViewById(R.id.signinbtnmainactivity);
        btnforgotpassword = findViewById(R.id.btnforgotpassword);
        progressBar2 = findViewById(R.id.progressBar2);


        // Initialising Firebase Auth
        Auth = FirebaseAuth.getInstance();
        // Initialising Firebase Auth

        //******************************************************************************************
        // SharedPreferences 2nd Trial
        //"autoLogin" is a unique string to identify the instance of this shared preference
        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j = sharedpreferences.getInt("key", 0);
        //Default is 0 so autologin is disabled
        if(j > 0){
            Intent activity = new Intent(getApplicationContext(), HomePage.class);
            startActivity(activity);
        }
        // SharedPreferences 2nd Trial
        //******************************************************************************************


        btnforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, ForgotPasswordActivity.class));
                // No finish method: On Clicking Back Button, Return to Login
//                finish();

            }
        });


        backbtnsignin = findViewById(R.id.backbtnCoBed);
        backbtnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                finish();
            }
        });

        signupbtnsigninactivity = findViewById(R.id.signupbtnsigninactivity);
        signupbtnsigninactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        signinbtnmainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }

            // Method to Check all fields are filled.
            private void validateUserData() {

                // Syntax to Hide Virtual Keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(signinbtnmainactivity.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                // Syntax to Hide Virtual Keyboard

                logEmail = loginEmailSigninActivity.getText().toString();
                logPassword = loginPasswordSigninActivity.getText().toString();

                if (logEmail.isEmpty()){
                    loginEmailSigninActivity.setError("Please enter your Email Address.");
                    loginEmailSigninActivity.requestFocus();
                } else if(logPassword.isEmpty()){
                    loginPasswordSigninActivity.setError("Please fill in your Password.");
                    loginPasswordSigninActivity.requestFocus();
                } else {
                    loginUser();
                }


            }

            // Method to Sign in User
            private void loginUser() {

                progressBar2.setVisibility(View.VISIBLE);
                Auth.signInWithEmailAndPassword(logEmail, logPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar2.setVisibility(View.INVISIBLE);

                                    //OFF
//                                    // SharedPreferences
//                                    editor.putString("isLogin","yes");
//                                    editor.apply();
//                                    // SharedPreferences
                                    //*****************************Keep User Logged In*************************************************************
                                    // SharedPreferences 2nd Trial
                                    //Once you click login, it will add 1 to shredPreference which will allow autologin in onCreate
                                    autoSave = 1;
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putInt("key", autoSave);
                                    editor.apply();
                                    // SharedPreferences 2nd Trial
                                    //*****************************Keep User Logged In*************************************************************

                                    openMain();
                                } else {
                                    progressBar2.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SigninActivity.this, "Error :"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SigninActivity.this, "Error :"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    // Method to go to Home Page after Sign in
    private void openMain(){
        startActivity(new Intent(SigninActivity.this, HomePage.class));
        finish();

    }
}