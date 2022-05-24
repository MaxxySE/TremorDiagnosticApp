package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.hss.heatmaplib.HeatMap;

public class SpiralActivity extends AppCompatActivity {


    public int constWidth = 1080, constSize = 15, countsOfTurns = 2, turnsAmount = 30;
    public float width, height, size, xtmp, ytmp;

    public ChangePage changePage = new ChangePage();
    public SpiralView spiralView;
    public HeatMap heatMap;
    public String fileName="";
    public File file;
    public DataToJson dataToJson;
    public Button nextSpiralBtn;
    public NewFile newFile = new NewFile();

    public List<Float> xDataMap = new ArrayList<Float>();
    public List<Float> yDataMap = new ArrayList<Float>();
    public List<Float> xDataSpiral = new ArrayList<Float>();
    public List<Float> yDataSpiral = new ArrayList<Float>();
    public List<Float> delta = new ArrayList<Float>();
    public List<Float> xClosest = new ArrayList<Float>();
    public List<Float> yClosest = new ArrayList<Float>();

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
        defineSpiralSize();
        fullScreenMode();
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

    public void fullScreenMode(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    public void getInputtedData(){
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            fileName = arguments.getString("sentStr");
        }
    }

    public void firstDraw(){spiralView.setup(this, width, height, size, turnsAmount);}

    public void defineSpiralSize(){ size = width/constWidth * constSize; }


    public void onDrawNext(View view){
        saveAndUpdateData();
        dataToJson();
        sendDataToServer();
        //deleteTmpFiles();
        switch(turnsAmount){
            case 30:
                constSize = 12; turnsAmount = 40; countsOfTurns = 3;
                break;
            case 40:
                constSize = 10; turnsAmount = 50; countsOfTurns = 4;
                break;
            case 50:
                Toast.makeText(getApplicationContext(),"Результат сохранен.", Toast.LENGTH_SHORT).show();
                changePage.moveFromTo(this, MainActivity.class, "");
                break;
        }
        refreshSpiralCoors();
        defineSpiralSize();
        spiralView.setup(this, width, height, size, turnsAmount);
        getSpiralCoords();
    }

    public void heatMapDraw(MotionEvent event){
        if(event.getAction() != MotionEvent.ACTION_UP){
            float x = event.getX() / width;
            float y = event.getY() / height;

            heatMapCoordsList(x, y);

            heatMap.setRadius(270d);
            heatMap.setMinimum(0.0);
            heatMap.setMaxDrawingWidth(400);
            heatMap.setMaximum(100.0);
            HeatMap.DataPoint point = new HeatMap.DataPoint(x, y, 20f);
            heatMap.addData(point);
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

    public void takeScreenShot(){
        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAndUpdateData(){
        file = newFile.createFile(fileName, countsOfTurns,
                ".jpg", "DiagnosticData_Picture","Pictures", this);
        nextSpiralBtn.setVisibility(View.INVISIBLE);
        spiralView.setVisibility(View.INVISIBLE);

        heatMap.forceRefresh();
        getSpiralCoords();
        countClosestPoint();

        // --------------------- for debug --------------------- //
//        System.out.print("LIST SIZE:" + xdataspiral.size() + "\n\n");
//        for(int i=0;i<xdataspiral.size();i++){
//            System.out.println("XData: " + xdata.get(i) + " TURNS: " + countsOfTurns);
//            System.out.println("YData: " + ydata.get(i) + " TURNS: " + countsOfTurns + "\n\n");
//        }

//        System.out.println("LIST SIZE:" + xDataMap.size() + "\n\n");
//        for(int i = 0; i < xDataMap.size(); i++){
//            System.out.println("XData: " + xDataMap.get(i) + " TURNS: " + countsOfTurns);
//            System.out.println("YData: " + yDataMap.get(i) + " TURNS: " + countsOfTurns + "\n\n");
//        }
        System.out.print("\n\n");
        for(int k = 0; k < delta.size(); k++){
            System.out.print("Closest X: " + xClosest.get(k) + " Closest Y: " + yClosest.get(k)
                    + " Delta: " + delta.get(k) + "\n");
        }

        // --------------------- for debug --------------------- //

        //textWait.setVisibility(View.INVISIBLE);

        takeScreenShot();
        nextSpiralBtn.setVisibility(View.VISIBLE);
        spiralView.setVisibility(View.VISIBLE);
        heatMap.clearData();
        heatMap.forceRefresh();
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
            delta.add(pointDiff);
            xClosest.add(xtmp);
            yClosest.add(ytmp);
            pointDiff = 1;
        }
    }

    public void dataToJson(){
        dataToJson = new DataToJson(this);
        dataToJson.setData(xClosest, yClosest, delta);
        dataToJson.saveDataToJson(fileName, countsOfTurns);
        refreshCoordsForJson();
    }

    public void refreshCoordsForJson(){
        xClosest.clear();
        yClosest.clear();
        delta.clear();
        xDataMap.clear();
        yDataMap.clear();
    }

    public void refreshSpiralCoors(){
        xDataSpiral.clear();
        yDataSpiral.clear();
    }

    public void sendDataToServer(){
        //TODO: there is I should send the collected data from the user to the server
        // that will process it
        if(file.exists()){
            System.out.println("Pic file still exists");
        }
        if(dataToJson.jsonFile.exists()){
            System.out.println("JSON file still exists");
        }
    }

    public void deleteTmpFiles(){
        file.delete();
        dataToJson.jsonFile.delete();
        if(!file.exists()){
            System.out.println("Pic file has been deleted");
        }
        if(!dataToJson.jsonFile.exists()){
            System.out.println("JSON file has been deleted");
        }
    }

    public void onBackToMain(View view){
        //deleteTmpFiles();
        changePage.moveFromTo(this, MainActivity.class, "");
        //TODO: a data will deletes by itself when user will переходить on the next spiral and save only a result
        // also erase data if the user closes the app
        // Resize heatmap properties in dependence of screen resolution!!!
    }
}