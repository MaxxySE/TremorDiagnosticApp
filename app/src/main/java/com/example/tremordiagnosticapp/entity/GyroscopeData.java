package com.example.tremordiagnosticapp.entity;

import java.util.ArrayList;
import java.util.List;

public class GyroscopeData {
    String name;
    String dateTime;
    List<GyroscopeDataEntry> data = new ArrayList<>();

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDateTime() {return dateTime;}

    public void setDateTime(String dateTime) {this.dateTime = dateTime;}

    public List<GyroscopeDataEntry> getData() {return data;}

    public void setData(List<GyroscopeDataEntry> data) {this.data = data;}
}
