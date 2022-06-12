package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.tremordiagnosticapp.entity.SpiralData;
import com.example.tremordiagnosticapp.entity.SpiralDataEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.hss.heatmaplib.HeatMap;

public class SpiralDataReceivingPage extends AppCompatActivity {


    public int constWidth = 1080, constSize = 15, countsOfTurns = 2, turnsAmount = 30;
    public float width, height, size, xtmp, ytmp;

    public ChangePage changePage = new ChangePage();
    public SpiralData spiralData = new SpiralData();
    SendDataToServer sendDataToServer = new SendDataToServer();
    public GetCurrentDateAndTime getCurrentDateAndTime = new GetCurrentDateAndTime();
    public ReceiveDataFromServer receiveDataFromServer = new ReceiveDataFromServer();
    public SpiralView spiralView;
    public HeatMap heatMap;
    public String fileName="";
    public Button nextSpiralBtn;


    public List<Float> xDataMap = new ArrayList<Float>();
    public List<Float> yDataMap = new ArrayList<Float>();
    public List<Float> xDataSpiral = new ArrayList<Float>();
    public List<Float> yDataSpiral = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        heatMapDraw(event);
        return true;
    }

    public void init(){
        getInputtedData();
        screenSizeDefiner();
        variablesDefiner();
        screenModeDefiner();
        defineSpiralSize();
        firstDraw();
    }

    public void variablesDefiner(){
        spiralView = findViewById(R.id.spiralDrawView);
        heatMap = findViewById(R.id.heatmap);
        nextSpiralBtn = findViewById(R.id.nextSpiralBtn);
    }

    public void screenSizeDefiner(){
        Display display = getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        display.getRealSize(outSize);
        width = outSize.x;
        height = outSize.y;
    }

    public void screenModeDefiner(){
        new EnableFullscreenMode(this);
    }

    public void getInputtedData(){
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            fileName = arguments.getString("sentStr");
        }
    }

    public void defineSpiralSize(){ size = width/constWidth * constSize; }

    public void firstDraw(){spiralView.setup(this, width, height, size, turnsAmount);}

    public void onDrawNext(View view){
        saveAndUpdateData();
        switch(turnsAmount){
            case 30:
                constSize = 12; turnsAmount = 40; countsOfTurns = 3;
                break;
            case 40:
                constSize = 10; turnsAmount = 50; countsOfTurns = 4;
                break;
            case 50:
                startReceivingDataFromServer(fileName);
                changePage.moveFromTo(this, MainPage.class, fileName, "");
                break;
        }
        refreshSpiralCoors();
        defineSpiralSize();
        spiralView.setup(this, width, height, size, turnsAmount);
        getSpiralCoords();
    }

    public void startReceivingDataFromServer(String fileName){
        receiveDataFromServer.getData(fileName, getCurrentDateAndTime.getCurrentDateAndTime());
        new Thread(receiveDataFromServer).start();
    }

    public void heatMapDraw(MotionEvent event){
        if(event.getAction() != MotionEvent.ACTION_UP){
            float x = event.getX() / width;
            float y = event.getY() / height;

            heatMapCoordsList(x, y);

        }
    }

    public void heatMapCoordsList(float x, float y){
        xDataMap.add(x);
        yDataMap.add(y);
    }

    public void getSpiralCoords(){
        this.xDataSpiral = spiralView.getXCoords();
        this.yDataSpiral = spiralView.getYCoords();
    }

    public void saveAndUpdateData(){
        getSpiralCoords();
        countClosestPoint();

        spiralData.setTurns(countsOfTurns);
        spiralData.setName(fileName);

        System.out.print(spiralData.getData().size() + "\n");
        System.out.print(spiralData + "\n");

        dataToJson();
    }

    public void countClosestPoint(){
        float pointDist, pointDiff = 1;
        for(int i=0; i<xDataMap.size(); i++){
            for(int j=0; j<xDataSpiral.size();j++){
                pointDist = (float) Math.sqrt(Math.pow((xDataSpiral.get(j)-xDataMap.get(i)),2) +
                        Math.pow((yDataSpiral.get(j) - yDataMap.get(i)),2));
                if(pointDiff >= pointDist){
                    pointDiff = pointDist;
                    xtmp = xDataMap.get(i);
                    ytmp = yDataMap.get(i);
                }
            }
            SpiralDataEntry spiralDataEntry = new SpiralDataEntry(xtmp, ytmp, pointDiff);
            spiralData.getData().add(spiralDataEntry);
            pointDiff = 1;
        }
    }

    public void dataToJson(){
        spiralData.setDateTime(getCurrentDateAndTime.getCurrentDateAndTime());
        Gson gson = new Gson();
        String str = gson.toJson(spiralData);
        DataSaver.writeDataToFile(fileName, "SpiralData", str);
        sendDataToServer.getFilePath(fileName, "SpiralData");
        refreshCoordsForJson();
    }

    public void refreshCoordsForJson(){
        spiralData = new SpiralData();
        xDataMap.clear();
        yDataMap.clear();
    }

    public void refreshSpiralCoors(){
        xDataSpiral.clear();
        yDataSpiral.clear();
    }


    public void onBackToMain(View view){
        changePage.moveFromTo(this, MainPage.class, "", "");
    }
}