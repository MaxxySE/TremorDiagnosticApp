package com.example.tremordiagnosticapp;

import com.example.tremordiagnosticapp.entity.SavedData;
import com.google.gson.Gson;

import java.util.List;

public class SaveViaGson {
    public void save(List<SavedData> data, String fileName, String dataSubType){
        Gson gson = new Gson();
        String str = gson.toJson(data);
        DataSaver.writeDataToFile(fileName, dataSubType, str);
    }
}
