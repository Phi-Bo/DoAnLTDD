package com.example.neko.Utills;

public class Comments {
    public String userName,userImage,comment;

    public Comments(String userName, String userImage, String comment) {
        this.userName = userName;
        this.userImage = userImage;
        this.comment = comment;
    }

    public Comments() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
