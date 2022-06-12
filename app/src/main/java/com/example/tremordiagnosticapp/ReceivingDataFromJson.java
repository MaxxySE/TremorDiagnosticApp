package com.example.tremordiagnosticapp;

import com.example.tremordiagnosticapp.entity.SavedData;

import java.io.File;

public class ReceivingDataFromJson {
    public ParsingJsonFile parsingJsonFile = new ParsingJsonFile();
    public GenerateNewFile generateNewFile = new GenerateNewFile();
    private SavedData[] list;


    public SavedData[] receivingData(){
        generateNewFile.setFileDir("User", ".json", "JSON", "Results");
        File file = new File(generateNewFile.fileDir);

        if(file.exists()){
            try {
                list = parsingJsonFile.getStringFromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
