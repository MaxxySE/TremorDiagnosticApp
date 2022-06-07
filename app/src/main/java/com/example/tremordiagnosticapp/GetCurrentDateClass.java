package com.example.tremordiagnosticapp;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentDateClass {
    public String getCurrentDateAndTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return sdf.format(new Date());
    }
}
