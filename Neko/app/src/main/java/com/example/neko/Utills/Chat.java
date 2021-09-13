package com.example.neko.Utills;

public class Chat {
    public  String sms;
    public String userid;
    public String isdelete;

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getStatusMess() {
        return statusMess;
    }

    public void setStatusMess(String statusMess) {
        this.statusMess = statusMess;
    }

    public String statusMess;

    public Chat() {
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public Chat(String sms, String userid, String isdelete, String statusMess) {
        this.sms = sms;
        this.userid = userid;
        this.isdelete = isdelete;
        this.statusMess = statusMess;
    }

}
