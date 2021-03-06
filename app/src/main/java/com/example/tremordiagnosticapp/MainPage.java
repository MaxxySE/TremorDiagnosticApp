package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    public TextView fileNameField;
    public String fileName;
    ChangePage changePage = new ChangePage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        variablesDefiner();
        verifyStoragePermissions(this);
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public void variablesDefiner(){ fileNameField = findViewById(R.id.fileNameField); }

    public void getFileName(){ fileName = fileNameField.getText().toString(); }

    public void onContinueToSpiralPage(View view){
        getFileName();
        if (!fileName.equals("")) {
            changePage.moveFromTo(this, SpiralDataReceivingPage.class, fileName, "");
        } else {
            Toast.makeText(this,
                    "Вы не ввели название файла.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onSavedResults(View view){
        changePage.moveFromTo(this, SavedResultsPage.class, "", "");
    }

}
