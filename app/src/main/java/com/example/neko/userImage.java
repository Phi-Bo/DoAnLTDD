package com.example.neko;

import java.util.Date;

public class userImage {


    public String getImageAvatar() {
        return imageAvatar;
    }

    public void setImageAvatar(String imageAvatar) {
        this.imageAvatar = imageAvatar;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    private  String imageAvatar;
    private  String phoneNumber;

    public String getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(String idPoster) {
        this.idPoster = idPoster;
    }

    private  String idPoster;

    public  String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private  String updateTime;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    private  String discription;
    // đôi tương tấm poster
    public userImage(String imageAvatar,String updateTime,String discription, String idPoster) {
        this.imageAvatar=imageAvatar;
        this.discription= discription;
        this.updateTime= updateTime;
        this.idPoster = idPoster;
    }
    // đối tượng ảnh đại diện
    public userImage(String imageAvatar,String phoneNumber,String updateTime) {
        this.imageAvatar=imageAvatar;
        this.phoneNumber= phoneNumber;
        this.updateTime= updateTime;
    }

    public userImage() {
    }
}
