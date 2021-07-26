package com.example.finalproject.models;

import android.net.Uri;

import java.util.concurrent.atomic.AtomicInteger;

public class Homework {
    private String subject, description, date;
    private int id;
    private String path = "";
    public static final AtomicInteger counter = new AtomicInteger();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Homework() {}

    public Homework(String subject, String description, String date,String path) {
        this.id = counter.getAndIncrement();
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.path = path;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
