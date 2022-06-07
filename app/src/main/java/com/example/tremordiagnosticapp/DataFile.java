package com.example.tremordiagnosticapp;


import android.os.Environment;

import java.io.File;

public class DataFile {

    private GetCurrentDateClass getCurrentDateClass = new GetCurrentDateClass();
    public String fileDir = "";



    public void setFileDir(String fileName,
                           String fileType, String dataType, String dataSubtype){
        String currentDateAndTime = getCurrentDateClass.getCurrentDateAndTime();
        fileDir = Environment.getExternalStorageDirectory() + "/" + "Documents"
                + "/TremorDataCollector/" + dataType + "/" + dataSubtype + "/"
                + currentDateAndTime + "_"+ fileName + "_" + dataSubtype + fileType;
        //Temporary hardcoded "Documents" in the path string
    }

    public File createFile(String fileName,
                           String fileType, String dataType, String dataSubtype){
        setFileDir(fileName, fileType, dataType, dataSubtype);
        return new File(fileDir);
    }

}

