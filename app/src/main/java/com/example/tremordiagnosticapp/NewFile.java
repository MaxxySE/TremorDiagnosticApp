package com.example.tremordiagnosticapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewFile {
    private String currentDateAndTime, fileDir = "";

    public void getCurrentDateAndTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        currentDateAndTime = sdf.format(new Date());
    }

    public void setFileDir(String fileName, Integer countsOfTurns,
                           String fileType, String dataType, String dataTypeFolder, Context context){
        getCurrentDateAndTime();
        fileDir = Environment.getExternalStorageDirectory() + "/" + dataTypeFolder  + "/" + dataType + "/NAME_"+
                fileName + " DATE_" + currentDateAndTime + " TURNS_" +
                countsOfTurns + fileType;
        System.out.println(fileDir);
    }

    public File createFile(String fileName, Integer countsOfTurns,
                           String fileType, String dataType, String dataTypeFolder, Context context){
        setFileDir(fileName, countsOfTurns, fileType, dataType, dataTypeFolder, context);
        // File file = new File(fileDir);
        return new File(fileDir);
    }
}
