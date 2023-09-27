package com.example.applogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class loginactivity extends AppCompatActivity {
    TextView username1;
    TextView password1;
    MaterialButton loginbtn1;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        username1 = (TextView) findViewById(R.id.username1);
        password1 = (TextView) findViewById(R.id.password1);
        loginbtn1 = (MaterialButton) findViewById(R.id.signbtn1);
        db = new DBHelper(this);

        loginbtn1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             String user = String.valueOf(username1.getText());
                                             String pass = String.valueOf(password1.getText());
                                             Boolean checkuser = db.checkpassname(user, pass);
                                             if (checkuser) {
                                                 Intent it = new Intent(getApplicationContext(), recycle.class);
                                                 it.putExtra(MainActivity.EXTRA_USERNAME, username1.getText().toString());
                                                 startActivity(it);
                                             } else {
                                                 Toast.makeText(loginactivity.this, "user or pass not found please enter a valid info", Toast.LENGTH_SHORT).show();
                                             }

                                         }
                                     }
        );


    }

}
