package com.example.tremordiagnosticapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.View;
import android.widget.Button;


import com.example.tremordiagnosticapp.entity.GyroscopeData;
import com.example.tremordiagnosticapp.entity.GyroscopeDataEntry;
import com.google.gson.Gson;

public class ThreadGyroscopeData implements Runnable, SensorEventListener {

    public SensorManager sensorManager;
    private Context mContext;
    public String fileName;
    private final int mSeconds = 7000;
    private final int stepPause = 10;
    public int counter = 0;
    public int collected = 0;
    private Handler handler;
    private Button dataReceiveBtn;


    //public List<AccelerometerDataEntry> accelerometerData = new ArrayList<>();
    public GyroscopeData gyroscopeData = new GyroscopeData();
    public GetCurrentDateClass getCurrentDateClass = new GetCurrentDateClass();
    private GyroscopeDataEntry entry = null;
    public VibrationClass vibrationClass = new VibrationClass();
    public ChangePage changePageClass = new ChangePage();
    public SendDataToServer sendDataToServer = new SendDataToServer();



    @Override
    public void run() {

        sensorManager=(SensorManager)this.mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);



        for(int i = 0; i < mSeconds/stepPause; i++){
            try {
                Thread.sleep(stepPause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.entry != null) {

                gyroscopeData.getData().add(this.entry);

                collected += 1;
                System.out.println("Collected sensor data: " + collected);
            }
        }

        handler.post(() -> {
                stopSensors();
                dataReceiveBtn.setVisibility(View.VISIBLE);
                collected = 0;
                saveDataToJson();
                vibrationClass.activateVibration(mContext);
                changePageClass.moveFromTo(mContext, MainActivity.class, "", "");
                sendDataToServer.getFilePath(fileName, "AccelerometerData");
        });

    }

    public void getData(Context mContext, Handler handler, Button dataReceiveBtn, String fileName){
        this.mContext = mContext;
        this.handler = handler;
        this.dataReceiveBtn = dataReceiveBtn;
        this.fileName = fileName;
        gyroscopeData.setName(fileName);
    }

    public void stopSensors(){
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType()==Sensor.TYPE_GYROSCOPE) {
            entry = new GyroscopeDataEntry(
                    sensorEvent.values[0],
                    sensorEvent.values[1],
                    sensorEvent.values[2]
            );

            this.entry = entry;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    public void saveDataToJson(){
        gyroscopeData.setDateTime(getCurrentDateClass.getCurrentDateAndTime());
        Gson gson = new Gson();
        String str = gson.toJson(this.gyroscopeData);
        // [{"x" : 1, "y" : 1, "z" : 1}]
        DataSaver.writeDataToFile(fileName, "AccelerometerData", str);
    }


}
