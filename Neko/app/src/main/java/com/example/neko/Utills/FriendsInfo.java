package com.example.neko.Utills;

public class FriendsInfo {
    public String friendsName,friendsPhonenumber,urifriends;

    public FriendsInfo() {
    }

    public String getFriendsName() {
        return friendsName;
    }

    public void setFriendsName(String friendsName) {
        this.friendsName = friendsName;
    }

    public String getFriendsPhonenumber() {
        return friendsPhonenumber;
    }

    public void setFriendsPhonenumber(String friendsPhonenumber) {
        this.friendsPhonenumber = friendsPhonenumber;
    }

    public String getUrifriends() {
        return urifriends;
    }

    public void setUrifriends(String urifriends) {
        this.urifriends = urifriends;
    }

    public FriendsInfo(String friendsName, String friendsPhonenumber, String urifriends) {
        this.friendsName = friendsName;
        this.friendsPhonenumber = friendsPhonenumber;
        this.urifriends = urifriends;
    }
}
