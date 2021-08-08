package com.example.neko.Utills;

public class Userprofile {
    public String number,password,uriAvatar,username;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Userprofile() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Userprofile(String number, String password, String uriAvatar, String username) {
        this.number = number;
        this.password = password;
        this.uriAvatar = uriAvatar;
        this.username = username;
    }
}
