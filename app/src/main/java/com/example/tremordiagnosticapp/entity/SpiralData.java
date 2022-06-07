package com.example.tremordiagnosticapp.entity;

import java.util.ArrayList;
import java.util.List;


public class SpiralData {
    String name;
    String dateTime; //TODO: receive current time
    float turns;

    List<SpiralDataEntry> data = new ArrayList<>();

    public float getTurns() {return turns;}

    public void setTurns(float turns) {this.turns = turns;}

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<SpiralDataEntry> getData() {
        return data;
    }

    public void setData(List<SpiralDataEntry> data) {
        this.data = data;
    }
}
