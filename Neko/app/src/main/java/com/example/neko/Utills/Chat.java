package com.example.neko.Utills;

public class Chat {
    public  String sms,userid,isdelete,statusMess;

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

    public Chat(String sms, String userid) {
        this.sms = sms;
        this.userid = userid;
    }
}
