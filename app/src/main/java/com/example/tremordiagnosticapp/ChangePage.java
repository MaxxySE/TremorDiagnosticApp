package com.example.tremordiagnosticapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ChangePage {

    public void moveFromTo(Context context, Class page, String sentStr, String sentStr1){
        Intent intent = new Intent(context, page);
        intent.putExtra( "sentStr", sentStr);
        intent.putExtra( "sentStr1", sentStr1);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

}
