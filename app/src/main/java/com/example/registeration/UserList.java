package com.example.registeration;

public class UserList {
    String userID;
    String userPassword;
    String userGender;
    String userMajor;
    String userEmail;

    public UserList(String userID, String userPassword, String userGender, String userMajor, String userEmail) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userMajor = userMajor;
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
