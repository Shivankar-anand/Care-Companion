package com.example.locale;

import com.google.firebase.database.ServerValue;

public class Assignment {

    private String postKey;
    private String test_name;
    private String test_details;
    private String test_price;
    private String test_image;
    private String test_number;
    private String userId;
    private String userPhoto;
    private String userName;
    private Object timeStamp ;

    public Assignment() {
    }

    public Assignment(String test_name, String test_details, String test_price, String test_image, String test_number, String userId, String userPhoto, String userName) {
        this.test_name = test_name;
        this.test_details = test_details;
        this.test_price = test_price;
        this.test_image = test_image;
        this.test_number = test_number;
        this.userId = userId;
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_details() {
        return test_details;
    }

    public void setTest_details(String test_details) {
        this.test_details = test_details;
    }

    public String getTest_price() {
        return test_price;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public String getTest_image() {
        return test_image;
    }

    public void setTest_image(String test_image) {
        this.test_image = test_image;
    }

    public String getTest_number() {
        return test_number;
    }

    public void setTest_number(String test_number) {
        this.test_number = test_number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
