package com.example.neko.Utills;

public class FriendsProfile {
    public String getUrifriends() {
        return urifriends;
    }

    public void setUrifriends(String urifriends) {
        this.urifriends = urifriends;
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

    public FriendsProfile(String friendsName, String friendsPhonenumber, String urifriends) {
        this.friendsName = friendsName;
        this.friendsPhonenumber = friendsPhonenumber;
        this.urifriends= urifriends;
    }

    public FriendsProfile() {
    }
    public String friendsPhonenumber;
    public  String urifriends;
    public String friendsName;

}
