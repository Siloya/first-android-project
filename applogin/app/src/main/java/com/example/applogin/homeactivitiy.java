package com.example.applogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class homeactivitiy extends AppCompatActivity {

    EditText namc , nbrc;
    Button addbtn, viewbtn;
    String mUsername;
 TextView tittle;
 DBHelper db;
    //public static final String EXTRA_USERNAME1 = mUsername;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        Intent it = getIntent();
        Bundle bdl;
        if (it != null && (bdl = it.getExtras()) != null) {
            mUsername = bdl.getString(MainActivity.EXTRA_USERNAME);
        }

        namc = (EditText) findViewById(R.id.namec);
        nbrc = (EditText) findViewById(R.id.nbrc);
        addbtn = (Button) findViewById(R.id.addbtn);
        viewbtn = (Button) findViewById(R.id.viewbtn);
        tittle =(TextView) findViewById(R.id.tittle);
        tittle.setText("hello " + mUsername);
        db = new DBHelper(this);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ctname = namc.getText().toString();
                String ctnbr= nbrc .getText().toString();

              if(ctname.equals("") || ctnbr.equals("")){ Toast.makeText(homeactivitiy.this, "PLEASE FILL ALL THE FIELDES ", Toast.LENGTH_LONG).show();}
              else{     Boolean rslt =  db.insertcontact(ctname,ctnbr,mUsername);
              if (rslt){
                  Toast.makeText(homeactivitiy.this, "user add", Toast.LENGTH_LONG).show();
              }}

            }});

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // finish();
                Intent it = new Intent(getApplicationContext(), recycle.class);
                it.putExtra(MainActivity.EXTRA_USERNAME, mUsername);
                startActivity(it);
            }});


    }

}
