package com.gayo.maru.model;

import java.util.Date;

public class meetModel {
    String meetLeader;
    String room;
    String[] mails;
    Date date;

    public meetModel(String meetLeader, String room, String[] mails, Date date) {
        this.meetLeader = meetLeader;
        this.room = room;
        this.mails = mails;
        this.date = date;
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
}
