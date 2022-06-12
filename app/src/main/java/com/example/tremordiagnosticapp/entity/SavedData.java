package com.example.tremordiagnosticapp.entity;

public class SavedData {
    private String name;
    private String date;
    private String result;

    public SavedData(String name, String date, String result) {
        this.name = name;
        this.date = date;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
