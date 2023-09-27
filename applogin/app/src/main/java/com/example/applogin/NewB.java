package com.example.applogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NewB extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent it = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newblayout);
    }
}
