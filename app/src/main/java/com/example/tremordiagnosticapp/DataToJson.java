package com.example.tremordiagnosticapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataToJson {
    private List<Float> xClosest = new ArrayList<Float>();
    private List<Float> yClosest = new ArrayList<Float>();
    private List<Float> delta = new ArrayList<Float>();
    File jsonFile;
    private JSONArray jsonArray = new JSONArray();
    private MainActivity mainActivity = new MainActivity();

    private Context context;

    private NewFile newFile = new NewFile();

    public DataToJson(Context context){
        this.context = context;
    }

    public void setData(List<Float> xClosest, List<Float> yClosest, List<Float> delta){
        this.xClosest = xClosest;
        this.yClosest = yClosest;
        this.delta = delta;
        writeDataToJsonArray();
    }

    public void writeDataToJsonArray(){
        for(int i =0; i < delta.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("x", xClosest.get(i));
                jsonObject.put("y", yClosest.get(i));
                jsonObject.put("delta", delta.get(i));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDataToJson(String fileName, Integer countsOfTurns){
        writeDataToFile(fileName, countsOfTurns);
    }

    public void writeDataToFile(String fileName, Integer countsOfTurns){
        String fileType = ".json";
        String dataType = "DiagnosticData_JSON";
        jsonFile = newFile.createFile(fileName, countsOfTurns, fileType,
                dataType, "Documents", context);
        jsonFile.getParentFile().mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(jsonFile);
            byte[] buffer = jsonArray.toString().getBytes(StandardCharsets.UTF_8);
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //here will be code that should write and save data into json file
        //TODO: Need to somehow save json file on device
    }
}
