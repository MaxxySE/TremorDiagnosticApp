package com.example.tremordiagnosticapp;


import android.os.Environment;

import java.io.File;

public class GenerateNewFile {

    private GetCurrentDateAndTime getCurrentDateAndTime = new GetCurrentDateAndTime();
    String currentDateAndTime;
    public String fileDir = "";



    public void setFileDir(String fileName,
                           String fileType, String dataType, String dataSubtype){
        if(!dataSubtype.equals("Results")){
            currentDateAndTime = getCurrentDateAndTime.getCurrentDateAndTime();
        } else {
            currentDateAndTime = "List";
        }


        fileDir = Environment.getExternalStorageDirectory() + "/" + "Documents"
                + "/DiagnosticData/" + dataType + "/" + dataSubtype + "/"
                + currentDateAndTime + "_"+ fileName + "_" + dataSubtype + fileType;
        //Temporary hardcoded "Documents" in the path string
    }

    public File createFile(String fileName,
                           String fileType, String dataType, String dataSubtype){
        setFileDir(fileName, fileType, dataType, dataSubtype);
        return new File(fileDir);
    }

}

