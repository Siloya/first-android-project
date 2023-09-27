package com.example.applogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;


import com.google.android.material.button.MaterialButton;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "username";

    DBHelper db;
    TextView username;
    TextView password, rypassword , tnumbre;

    MaterialButton loginbtn, signnbutton;
    CountryCodePicker countryCodePicker;
   //// FirebaseAuth firebaseAuth;
   // PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    String codesent ;
    String phonenumber,countrycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        tnumbre =(TextView) findViewById(R.id.number);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        loginbtn = (MaterialButton) findViewById(R.id.login);
        signnbutton = (MaterialButton) findViewById(R.id.signbtn);
        rypassword = (TextView) findViewById(R.id.rypassword);
       // firebaseAuth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
               /* if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
                {Toast.makeText(MainActivity.this,"LOGIN SUCCES",Toast.LENGTH_SHORT);
                }else {Toast.makeText(MainActivity.this,"LOGIN failed",Toast.LENGTH_SHORT);}*/
                                            String user = username.getText().toString();
                                            String pass = password.getText().toString();
                                            String repass = rypassword.getText().toString();
                                          // String number=  tnumbre.getText().toString();
                                           /*
if(number.isEmpty())
{
Toast.makeText(getApplicationContext(), text: "Please Enter Your number", Toast.LENGTH_SHORT).show();
}
else if(number.length()<10)
{
Toast.makeText (getApplicationContext(), text: "Please Enter correct number", Toast.LENGTH_SHORT).show
                                             */
                                   /*         mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    super.onCodeSent(s, forceResendingToken);
                                                    Toast.makeText(MainActivity.this, "otp is sent ", Toast.LENGTH_LONG).show();
                                                    codesent = s ;


                                                }
                                            };*///number.equals("")

                                            if (user.equals("") || pass.equals("") || repass.equals("")) {
                                                Toast.makeText(MainActivity.this, "please fill all the fields and a valid valus", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (pass.equals(repass)) {
                                                    Boolean chekeuser = db.checkusername(user);
                                                    if (!chekeuser) {
                                                        Boolean adduser = db.insertdata(user, pass);
                                                        // ana zdt hy
                                                        // && FirebaseAuth.getInstance().getCurrentUser()!=null
                                                       if (adduser) {
                                                           /* phonenumber=countrycode+number;

                                                            PhoneAuthOptions options= PhoneAuthOptions.newBuilder (firebaseAuth)
                                                                    .setPhoneNumber (phonenumber)
                                                                    .setTimeout(  60L, TimeUnit.SECONDS)
                                                            .setActivity (MainActivity.this)
                                                                    .setCallbacks(mcallbacks)
                                                                    .build();
PhoneAuthProvider.verifyPhoneNumber(options);*/
                                                            Toast.makeText(MainActivity.this, "user added", Toast.LENGTH_SHORT).show();
                                                            Intent it = new Intent(getApplicationContext(), recycle.class);
                                                            it.putExtra(EXTRA_USERNAME, username.getText().toString());
                                                           // it.putExtra("otp",codesent);
                                                            startActivity(it);
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "user faled to add ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "user alrady exist ", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(MainActivity.this, "pASSWORD DIFFFERENT TO PASSWORD", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                        }//of methode
                                    }//of methode
        );

        signnbutton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               MainActivity.this.finish();
                                               Intent it = new Intent(getApplicationContext(), loginactivity.class);
                                               startActivity(it);
                                           }
                                       }
        );

        /*countryCodePicker = findViewById(R.id.countryCodePicker);
        countryCodePicker.setCountryForNameCode("LB"); // Set default country
        countrycode=countryCodePicker.getSelectedCountryCodeWithPlus();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
            //    countrycode = countryCodePicker.getSelectedCountryNameCode();
                countrycode=countryCodePicker.getSelectedCountryCodeWithPlus();
            }
        });*/
    }/*
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

        }
    }*/

}