package com.damilola.searchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;


public class Main2Activity extends AppCompatActivity {

    DBAssetHelper dBAssetHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dBAssetHelper = new DBAssetHelper(this);

        String full_name = getIntent().getStringExtra("full_name");

        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome: " + full_name);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}
