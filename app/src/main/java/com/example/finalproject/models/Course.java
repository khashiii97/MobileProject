package com.example.finalproject.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Course {

    private String subject,  teacher,  fromtime, totime, time,room;
    private int id,day;
    public static final AtomicInteger counter = new AtomicInteger();

    public Course(){

    }

    public Course(String subject, String teacher, String fromtime, String totime,int day,String room) {
        this.id = counter.getAndIncrement();
        this.subject = subject;
        this.teacher = teacher;
        this.fromtime = fromtime;
        this.totime = totime;
        this.day = day;
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
