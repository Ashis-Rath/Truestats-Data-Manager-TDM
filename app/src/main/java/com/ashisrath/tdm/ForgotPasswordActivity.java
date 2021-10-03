package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView backbtnForgetTosignin;
    TextView goToSigninPage;
    EditText recoverEmailText;
    Button resetBtn;
    String email;
    ProgressBar progressBar3;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        recoverEmailText = findViewById(R.id.recoverEmailText);
        progressBar3 = findViewById(R.id.progressBar3);

        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Syntax to Hide Virtual Keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(resetBtn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                // Syntax to Hide Virtual Keyboard
                validateData();
            }
        });



        goToSigninPage = findViewById(R.id.goToSigninPage);
        goToSigninPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(ForgotPasswordActivity.this, SigninActivity.class);
                startActivity(intenti);
            }
        });

        backbtnForgetTosignin = findViewById(R.id.backbtnForgetTosignin);
        backbtnForgetTosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void validateData(){
        email = recoverEmailText.getText().toString();
        if (email.isEmpty()) {
            recoverEmailText.setError("To Reset your Password, Enter the concerned E-mail here.");
        } else {
            forgetPass();
        }
    }

    private void forgetPass(){
        progressBar3.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressBar3.setVisibility(View.INVISIBLE);
                            Toast.makeText(ForgotPasswordActivity.this, "Please check your E-mail.", Toast.LENGTH_SHORT).show();
                            Intent intentm = new Intent(ForgotPasswordActivity.this, SigninActivity.class);
                            startActivity(intentm);
                            finish();
                        } else {
                            progressBar3.setVisibility(View.INVISIBLE);
                            Toast.makeText(ForgotPasswordActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}