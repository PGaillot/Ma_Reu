package com.gayo.maru.model;

import java.io.Serializable;
import java.util.Date;

public class MeetModel implements Serializable {

    String topic;
    String meetLeader;
    String room;
    String[] mails;
    Date date;
    int duration;


    public MeetModel(String meetLeader, String room, String[] mails, Date date, int duration, String topic) {
        this.topic = topic;
        this.meetLeader = meetLeader;
        this.room = room;
        this.mails = mails;
        this.date = date;
        this.duration = duration;
    }



    public String getMeetLeader() {
        return meetLeader;
    }

    public void setMeetLeader(String meetLeader) {
        this.meetLeader = meetLeader;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String[] getMails() {
        return mails;
    }

    public void setMails(String[] mails) {
        this.mails = mails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
