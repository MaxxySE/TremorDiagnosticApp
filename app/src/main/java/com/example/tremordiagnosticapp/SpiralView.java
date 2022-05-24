package com.example.tremordiagnosticapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpiralView extends View {

    private float x, y, angle;
    private List<Float> xdata = new ArrayList<Float>();
    private List<Float> ydata = new ArrayList<Float>();
    private float width, height, size, turnsAmount;
    private Paint paint = new Paint();

    public SpiralView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public SpiralView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public SpiralView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    public void init(@Nullable AttributeSet set){

    }

    public void setup(Context context, float width, float height, float size, float turnsAmount) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.turnsAmount = turnsAmount;

        invalidate();//Перерисовка вьюхи
    }

    public List<Float> getXCoords(){ return xdata; }

    public List<Float> getYCoords(){
        return ydata;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (float i = 0; i < this.turnsAmount; i += 0.01) {

            angle = 0.5f * i;

            x = this.width/2f + i * this.size * (float)Math.cos(angle);
            y = this.height/2f + i * this.size * (float)Math.sin(angle);

            xdata.add(x/width);
            ydata.add(y/height);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            canvas.drawPoint(x, y, paint);
        }
    }
}

