package com.example.tremordiagnosticapp.entity;

public class CustomResultListItem {
    private String id;
    private String username;
    private String date;
    private String result;

    public CustomResultListItem(String id, String username, String date, String result) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
