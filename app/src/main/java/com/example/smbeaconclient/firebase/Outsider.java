package com.example.smbeaconclient.firebase;

public class Outsider {
    private String token;
    private int floor;
    private boolean enter;

    public Outsider(String token, boolean enter) {
        this.token = token;
    }
    public Outsider() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }
}
