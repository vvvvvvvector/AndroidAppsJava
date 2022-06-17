package com.example.myproject.customclasses;

public class Task {
    private Boolean completed;
    private String text;
    private String date;
    private Long hour;
    private Long minute;

    public Task(Boolean completed, String text, String date, Long hour, Long minute) {
        this.completed = completed;
        this.text = text;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Long getMinute() {
        return minute;
    }

    public void setMinute(Long minute) {
        this.minute = minute;
    }
}
