package com.ashisrath.tdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView signinbtnsignupactivity;
    ImageView backbtnsignup;
    Spinner spinnerState, spinnerCity, spinnerService;
    EditText signupEmail, signupPassword, nameOrganisation, addressOrganisation, phoneOrganisation;
    Button signupbtnsignupactivity;
    String serviceType;
    String H = "Hospital", O = "Oxygen Supplier", C = "COVID Hospital";
    ProgressBar progressBar;


    String name, address, state, city, phone, Email, Password, category;

    ArrayAdapter<CharSequence> adapterCityTDM;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private DatabaseReference dbRef;
    //Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //findViewById(R.id." ")
        progressBar = findViewById(R.id.progressBar);
        nameOrganisation = findViewById(R.id.nameOrganisation);
        addressOrganisation = findViewById(R.id.addressOrganisation);
        phoneOrganisation = findViewById(R.id.phoneOrganisation);

        //Go to Sign in Page Button
        signinbtnsignupactivity = findViewById(R.id.signinbtnsignupactivity);
        signinbtnsignupactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        // apply plugin: 'com.google.gms.google-services'

        //Back Button
        backbtnsignup = findViewById(R.id.backbtnCoBed);
        backbtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Service Spinner
        spinnerService = findViewById(R.id.spinnerService);
        ArrayAdapter<CharSequence> adapterService = ArrayAdapter.createFromResource(this, R.array.Services, android.R.layout.simple_spinner_item);
        adapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(adapterService);
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //State Spinner
        spinnerState = findViewById(R.id.spinnerOxygenStatus);
        ArrayAdapter<CharSequence> adapterStateTDM = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapterStateTDM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapterStateTDM);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.YourCity, android.R.layout.simple_spinner_item);
                }
                if (position==1){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.AndamanandNicobar, android.R.layout.simple_spinner_item);
                }
                if (position==2){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.AndhraPradesh, android.R.layout.simple_spinner_item);
                }
                if (position==3){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ArunachalPradesh, android.R.layout.simple_spinner_item);
                }
                if (position==4){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Assam, android.R.layout.simple_spinner_item);
                }
                if (position==5){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Bihar, android.R.layout.simple_spinner_item);
                }
                if (position==6){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Chandigarh, android.R.layout.simple_spinner_item);
                }
                if (position==7){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Chattisgarh, android.R.layout.simple_spinner_item);
                }
                if (position==8){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.DadraandNagarHaveli, android.R.layout.simple_spinner_item);
                }
                if (position==9){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Damananddiu, android.R.layout.simple_spinner_item);
                }
                if (position==10){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Delhi, android.R.layout.simple_spinner_item);
                }
                if (position==11){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Goa, android.R.layout.simple_spinner_item);
                }
                if (position==12){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Gujurat, android.R.layout.simple_spinner_item);
                }
                if (position==13){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Haryana, android.R.layout.simple_spinner_item);
                }
                if (position==14){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.HimachalPradesh, android.R.layout.simple_spinner_item);
                }
                if (position==15){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.JammuandKashmir, android.R.layout.simple_spinner_item);
                }
                if (position==16){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Jharkhand, android.R.layout.simple_spinner_item);
                }
                if (position==17){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Karnataka, android.R.layout.simple_spinner_item);
                }
                if (position==18){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Kerala, android.R.layout.simple_spinner_item);
                }
                if (position==19){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Ladakh, android.R.layout.simple_spinner_item);
                }
                if (position==20){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Lakshadweep, android.R.layout.simple_spinner_item);
                }
                if (position==21){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.MadhyaPradesh, android.R.layout.simple_spinner_item);
                }
                if (position==22){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Maharastra, android.R.layout.simple_spinner_item);
                }
                if (position==23){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Manipur, android.R.layout.simple_spinner_item);
                }
                if (position==24){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Meghalaya, android.R.layout.simple_spinner_item);
                }
                if (position==25){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Mizoram, android.R.layout.simple_spinner_item);
                }
                if (position==26){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Nagaland, android.R.layout.simple_spinner_item);
                }
                if (position==27){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Odisha, android.R.layout.simple_spinner_item);
                }
                if (position==28){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Puducherry, android.R.layout.simple_spinner_item);
                }
                if (position==29){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Punjab, android.R.layout.simple_spinner_item);
                }
                if (position==30){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Rajasthan, android.R.layout.simple_spinner_item);
                }
                if (position==31){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Sikkim, android.R.layout.simple_spinner_item);
                }
                if (position==32){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.TamilNadu, android.R.layout.simple_spinner_item);
                }
                if (position==33){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Telangana, android.R.layout.simple_spinner_item);
                }
                if (position==34){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Tripura, android.R.layout.simple_spinner_item);
                }
                if (position==35){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.UttarPradesh, android.R.layout.simple_spinner_item);
                }
                if (position==36){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Uttarakhand, android.R.layout.simple_spinner_item);
                }
                if (position==37){
                    adapterCityTDM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.WestBengal, android.R.layout.simple_spinner_item);
                }

                adapterCityTDM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(adapterCityTDM);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCity = findViewById(R.id.spinnerCity);

        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);

        signupbtnsignupactivity = findViewById(R.id.signupbtnsignupactivity);
        signupbtnsignupactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Syntax to Hide Virtual Keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(signupbtnsignupactivity.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                // Syntax to Hide Virtual Keyboard
                
                validateData();
            }
        });




    }

    //Firebase Papaya Coders
    //If mentioned Email ID already exists, Signin Page will open
    private void openSignin() {
        startActivity(new Intent(this,SigninActivity.class));
        finish();
    }

    //Spinner State Validation
    private void setSpinnerError(Spinner spinnerState, String error){
        View selectedView = spinnerState.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinnerState.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinnerState.performClick(); // to open the spinner list if error is found.

        }
    }
    //Spinner City Validation
    private void setSpinnerErrorCity(Spinner spinnerCity, String errorCity){
        View selectedView = spinnerCity.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinnerCity.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(errorCity); // actual error message
            spinnerCity.performClick(); // to open the spinner list if error is found.

        }
    }
    //Spinner Service Validation
    private void setSpinnerErrorService(Spinner spinnerService, String errorService){
        View selectedView = spinnerService.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinnerService.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(errorService); // actual error message
            spinnerService.performClick(); // to open the spinner list if error is found.

        }
    }


    //Check if all the fields are completed.
    private void validateData() {
        name = nameOrganisation.getText().toString();
        address = addressOrganisation.getText().toString();
        phone = phoneOrganisation.getText().toString();
        state = spinnerState.getSelectedItem().toString();
        city = spinnerCity.getSelectedItem().toString();
        category = spinnerService.getSelectedItem().toString();
        Email = signupEmail.getText().toString();
        Password = signupPassword.getText().toString();

        if (name.isEmpty()){
            nameOrganisation.setError("Please enter the Name of your Organisation.");
            nameOrganisation.requestFocus();
        } else if(address.isEmpty()){
            addressOrganisation.setError("Please enter the Address of your Organisation.");
            addressOrganisation.requestFocus();
        } else if(state.equals("Your State")){
            setSpinnerError(spinnerState, "Fill State");
        } else if(city.equals("Your City")){
            setSpinnerError(spinnerCity, "Fill City");
        } else if(phone.isEmpty()){
            phoneOrganisation.setError("Please enter a Valid Phone number of your Organisation.");
            phoneOrganisation.requestFocus();
        } else if(category.equals("You are?")){
            setSpinnerError(spinnerService, "Please select a Category.");
        } else if(Email.isEmpty()){
            signupEmail.setError("Please enter a Valid Email ID of your Organisation.");
            signupEmail.requestFocus();
        } else if(Password.isEmpty()){
            signupPassword.setError("Please enter a Valid Password of your Email ID.");
            signupPassword.requestFocus();
        } else {
            createUser();
        }

    }

    private void createUser(){
        progressBar.setVisibility(View.VISIBLE);
        String email = signupEmail.getText().toString();
        String password = signupPassword.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadData();

                        } else {
                            signupEmail.setText("");
                            signupPassword.setText("");
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, "Error :"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void uploadData() {

        //Sorting New TDM Data into Hospital, Oxygen Supplier & Medicine Supplier
        if (serviceType.equals(H)){
//            dbRef = reference.child("Hospital").child(state).child(city);
            dbRef = reference.child("Hospital");
        } else if (serviceType.equals(O)){
//            dbRef = reference.child("Oxygen Supplier").child(state).child(city);
            dbRef = reference.child("Oxygen Supplier");
        } else if (serviceType.equals(C)) {
//            dbRef = reference.child("Medicine Supplier").child(state).child(city);
            dbRef = reference.child("COVID Hospital");
        }
//        dbRef = reference.child("users");
        //Creating User with Randomly Generated Key
//        String key = dbRef.push().getKey();
        //Creating User with UID
//        String key = dbRef.child(mAuth.getCurrentUser().getUid()).getKey();
        String key = dbRef.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).getKey();

        HashMap<String, String> user = new HashMap<>();
        user.put("Key", key);
        user.put("Name", name);
        user.put("Address", address);
        user.put("Phone_number", phone);
        user.put("State", state);
        user.put("City", city);
        user.put("Category", category);
        user.put("E_mail", Email);

        assert key != null;
        dbRef.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            openSignin();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, "Hi! You are now a member. Sign in Now!", Toast.LENGTH_SHORT).show();
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignupActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
//                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    //Firebase Papaya Coders

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}