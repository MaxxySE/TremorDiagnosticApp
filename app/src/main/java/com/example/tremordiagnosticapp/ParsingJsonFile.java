package com.example.tremordiagnosticapp;

import com.example.tremordiagnosticapp.entity.SavedData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParsingJsonFile {


    public SavedData[] getStringFromFile(File fl) throws Exception {
        FileInputStream fin = new FileInputStream(fl);
        String str = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return fromJsonToArray(str);
    }

    public String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public SavedData[] fromJsonToArray(String str){
        Gson gson = new Gson();
        SavedData[] arrayOfElements = gson.fromJson(str, SavedData[].class);
        return arrayOfElements;
    }
}
