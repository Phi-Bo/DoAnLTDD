package com.example.neko.Utills;

public class userInfo {
    public String number;

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

    public userInfo() {
    }

    public userInfo(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

    public userInfo(String number, String password, String username, String uriAvatar) {
        this.number = number;
        this.password = password;
        this.username = username;
        this.uriAvatar = uriAvatar;
    }

    public   String password;
    private String username,uriAvatar;


    public String getUsername() {
        return username;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }


}
