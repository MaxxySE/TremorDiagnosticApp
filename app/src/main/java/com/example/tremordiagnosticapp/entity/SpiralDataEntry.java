package com.example.tremordiagnosticapp.entity;

public class SpiralDataEntry {
    private float x;
    private float y;
    private float delta;

    public SpiralDataEntry(float x, float y, float delta) {
        this.x = x;
        this.y = y;
        this.delta = delta;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }
}
