package com.example.tremordiagnosticapp;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationClass {
    public void activateVibration(Context context){
        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final VibrationEffect vibrationEffect1;

        // this is the only type of the vibration which requires system version Oreo (API 26)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            vibrationEffect1 = VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE);

            vibrator.cancel();
            vibrator.vibrate(vibrationEffect1);
        } else {
            vibrator.vibrate(400);
        }
    }
}
