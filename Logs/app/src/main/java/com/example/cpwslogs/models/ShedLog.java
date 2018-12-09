package com.example.cpwslogs.models;

/**
 * Created by shaafi on 20-Aug-18.
 * Email: a15shaafi.209@gmail.com
 */
public class ShedLog {
    private String temp, humidity, ammonia, treatment, date;
    private int shed;

    public ShedLog() {
    }

    public ShedLog(int shed, String temp, String humidity, String ammonia, String treatment, String date) {
        this.shed = shed;
        this.temp = temp;
        this.humidity = humidity;
        this.ammonia = ammonia;
        this.treatment = treatment;
        this.date = date;
    }

    public int getShed() {
        return shed;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getAmmonia() {
        return ammonia;
    }

    public void setAmmonia(String ammonia) {
        this.ammonia = ammonia;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
