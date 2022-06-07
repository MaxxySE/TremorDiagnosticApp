package com.example.tremordiagnosticapp;

import java.io.File;

public class SendDataToServer {

    private File file;
    private DataFile dataFile = new DataFile();

    public void getFilePath(String fileName, String dataSubType){
        dataFile.setFileDir(fileName, ".json", "JSON", dataSubType);
        file = new File(dataFile.fileDir);
        sendData();
        removeFile();
    }

    public void sendData(){
        if(file.exists()){
            System.out.println("File is exists: " + file);
        }
    }

    public void removeFile(){
        if(file.exists()){
            file.delete();
            System.out.println("File has been deleted: " + file);
        }
    }
}
