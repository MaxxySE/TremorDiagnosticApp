package com.example.tremordiagnosticapp;

import java.io.File;

public class SendDataToServer {

    private File file;
    public GenerateNewFile generateNewFile = new GenerateNewFile();


    public void getFilePath(String fileName, String dataSubType){
        generateNewFile.setFileDir(fileName, ".json", "JSON", dataSubType);
        file = new File(generateNewFile.fileDir);
        sendDataAndRemoveFile();
    }

    public void sendDataAndRemoveFile(){
        if(file.exists()){
            System.out.println("File is exists: " + file);
            file.delete();
        }
    }
}
