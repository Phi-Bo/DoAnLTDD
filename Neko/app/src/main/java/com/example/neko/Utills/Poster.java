package com.example.neko.Utills;

public class Poster  {
    public String discription,idPostor,imPoster,updatetime;

    public Poster(String discription, String idPostor, String imPoster, String updatetime) {
        this.discription = discription;
        this.idPostor = idPostor;
        this.imPoster = imPoster;
        this.updatetime = updatetime;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getIdPostor() {
        return idPostor;
    }

    public void setIdPostor(String idPostor) {
        this.idPostor = idPostor;
    }

    public String getImPoster() {
        return imPoster;
    }

    public void setImPoster(String imPoster) {
        this.imPoster = imPoster;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Poster() {
    }
}
