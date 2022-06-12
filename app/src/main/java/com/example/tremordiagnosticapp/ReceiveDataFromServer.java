package com.example.tremordiagnosticapp;

import com.example.tremordiagnosticapp.entity.SavedData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceiveDataFromServer implements Runnable{
    private boolean isDataReceived = false;
    private String fileName;
    private String currentDate;
    private List<SavedData> data = new ArrayList<>();


    @Override
    public void run() {
        while(!isDataReceived){
            //Do something to receive data from server
            dataReceiver();
        }
    }

    public void getData(String fileName, String currentDate){
        this.fileName = fileName;
        this.currentDate = currentDate;
    }


    private void dataReceiver(){
        //Receive data

        //if data is not received - isDataReceived will be still set on false

        //else if data has been received - change isDataReceived mark to true to stop the cycle
        // that checks is data did receive
        // -------------------------------------- //

        // Something that receiving data from server
        String result = "received data";
        if(!result.equals("")){
            SavedData savedData = new SavedData(fileName, currentDate, result);
            data.add(savedData);
            writeAndUpdateData();
            isDataReceived = true;
        }
    }

    private void writeAndUpdateData(){
        data.addAll(Arrays.asList(new ReceivingDataFromJson().receivingData()));
        new SaveViaGson().save(data, "User", "Results");
    }
}

//TODO: to remove object from json file i can depend on date when was created object and delete it
// or id

