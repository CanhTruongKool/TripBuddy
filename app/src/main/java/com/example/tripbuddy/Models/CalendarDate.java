package com.example.tripbuddy.Models;

public class CalendarDate {
    private String dayOfWeek;
    private String day;
    private boolean isSelected;

    public CalendarDate(String dayOfWeek, String day, boolean isSelected) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
        this.isSelected = isSelected;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDay() {
        return day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

