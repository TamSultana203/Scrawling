package com.example.testthis;

public class Item {

    String  image;
    String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String status;
    String uid;


    public Item(String resultUri, String setUserName, String setStatus, String currentUserID) {

        this.image = resultUri;
        this.name = setUserName;
        this.status = setStatus;
        this.uid = currentUserID;

    }

    public Item() {

    }
}
