package com.example.baothuc;

public class AlarmModel {
    String timeAlarm;
    int codeAlarm;
    public AlarmModel(int codeAlarm, String timeAlarm)
    {
        this.codeAlarm =  codeAlarm;
        this.timeAlarm = timeAlarm;
    }

    public String getTimeAlarm() {
        return timeAlarm;
    }

    public void setTimeAlarm(String timeAlarm) {
        this.timeAlarm = timeAlarm;
    }

    public int getCodeAlarm() {
        return codeAlarm;
    }

    public void setCodeAlarm(int codeAlarm) {
        this.codeAlarm = codeAlarm;
    }
}
