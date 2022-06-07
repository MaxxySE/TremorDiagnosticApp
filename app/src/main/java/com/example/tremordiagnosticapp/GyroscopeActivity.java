package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class GyroscopeActivity extends AppCompatActivity {

    public Button receiveAccDataBtn;
    public Handler handler = new Handler();
    public String fileName;

    public ThreadGyroscopeData threadGyroscopeData = new ThreadGyroscopeData();
    public VibrationClass vibrationClass = new VibrationClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        init();
    }
    public void init(){
        receiveAccDataBtn = findViewById(R.id.receiveAccDataBtn);
        getFileName();
    }

    public void getFileName(){
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            fileName = arguments.get("sentStr").toString();
        }
    }

    public void threadSleep(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onReceiveAccData(View view){
        vibrationClass.activateVibration(this);

        threadSleep(700); // ms

        threadGyroscopeData.getData(this, handler, receiveAccDataBtn, fileName);
        threadGyroscopeData.counter = 0;
        receiveAccDataBtn.setVisibility(View.INVISIBLE);
        new Thread(threadGyroscopeData).start();
    }

    //TODO: probably somewhere here I need to clear some data

}