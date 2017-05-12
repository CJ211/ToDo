package com.example.jay.todo;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo implements Serializable {
    private Date date;
    private Date toDoDate;
    private String title;
    private String description;
    private int status;
    private boolean fullDisplayed;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    private static DateFormat dateFormatDueDate = new SimpleDateFormat("dd/MM/yyyy");

    public ToDo() {
        this.date = new Date();
    }

    public ToDo(long time, String title, String description, long toDoDate, int status) {
        this.date = new Date(time);
        this.title = title;
        this.description = description;
        this.toDoDate = new Date(toDoDate);
        this.status = status;
    }
    //    public String getToDoDate() { return dateFormat.format(toDoDate); }
    public String getToDoDate() { return dateFormatDueDate.format(toDoDate); }


    public String getDate() {
        return dateFormat.format(date);
    }

    public long getTime() {
        return date.getTime();
    }
    public long getTimeDueDate() {
        return toDoDate.getTime();
    }

    public void setTime(long time) {
        this.date = new Date(time);
    }

    public void setTimeDueDate(long toDoDate) {
        this.toDoDate = new Date(toDoDate);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getShortDescription() {
        String temp = description.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }

    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }

    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }
    @Override
    public String toString() {
        return this.title;
    }
}

