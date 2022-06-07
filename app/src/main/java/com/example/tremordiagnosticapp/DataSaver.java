package com.example.tremordiagnosticapp;


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;


public class DataSaver<T> {

    private Context context;

    public DataSaver(Context context){
        this.context = context;
    }

    public static void writeDataToFile(String fileName, String dataSubtype, String gson){
        String fileType = ".json";
        String dataType = "JSON";
        DataFile dataFile = new DataFile();

        File file = dataFile.createFile(fileName, fileType,
                    dataType, dataSubtype);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = gson.getBytes(StandardCharsets.UTF_8);
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
