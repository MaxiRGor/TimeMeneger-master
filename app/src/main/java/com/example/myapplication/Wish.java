package com.example.myapplication;

public class Wish {

    public Wish(String id, String dayNull, String day, String monthNull, String month, String year, String text) {
        this.id = id;
        this.dayNull = dayNull;
        this.day = day;
        this.monthNull = monthNull;
        this.month = month;
        this.year = year;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private   String id;
    private  String dayNull;
    private  String day;
    private   String monthNull;
    private  String month;
    private String year;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDayNull() {
        return dayNull;
    }

    public void setDayNull(String dayNull) {
        this.dayNull = dayNull;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonthNull() {
        return monthNull;
    }

    public void setMonthNull(String monthNull) {
        this.monthNull = monthNull;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
