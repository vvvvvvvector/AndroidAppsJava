package com.example.firebasedatatimepicker;

public class Note {
    private String text;
    private Long hour;
    private Long minute;
    private String date;

    public Note(String text, Long hour, Long minute, String date) {
        this.text = text;
        this.hour = hour;
        this.minute = minute;
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getHour() {
        return hour;
    }

    public Long getMinute() {
        return minute;
    }

    public String getDate() {
        return date;
    }
}
