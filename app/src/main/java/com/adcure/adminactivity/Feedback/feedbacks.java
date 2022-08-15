  package com.adcure.adminactivity.Feedback;

public class feedbacks {
    private String Date,Time,comment,user_id,user_name;

    public feedbacks(String date, String time, String comment, String user_id, String user_name) {
        Date = date;
        Time = time;
        this.comment = comment;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public feedbacks() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
