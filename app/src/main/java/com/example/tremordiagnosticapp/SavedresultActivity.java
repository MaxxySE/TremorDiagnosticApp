package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SavedresultActivity extends AppCompatActivity {

    ChangePage changePage = new ChangePage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedresult);
    }


    public void onBackToMain(View view){
        changePage.moveFromTo(this, MainActivity.class, "", "");
    }
}