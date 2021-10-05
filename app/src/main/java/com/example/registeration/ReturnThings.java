package com.example.registeration;

public class ReturnThings {
    int thingsID;
    String userID;
    String thingsName;
    String thingsWhere;
    String thingsWhen;

    public ReturnThings(int thingsID, String userID, String thingsName, String thingsWhere, String thingsWhen) {
        this.thingsID = thingsID;
        this.userID = userID;
        this.thingsName = thingsName;
        this.thingsWhere = thingsWhere;
        this.thingsWhen = thingsWhen;
    }

    public int getThingsID() {
        return thingsID;
    }

    public void setThingsID(int thingsID) {
        this.thingsID = thingsID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getThingsName() {
        return thingsName;
    }

    public void setThingsName(String thingsName) {
        this.thingsName = thingsName;
    }

    public String getThingsWhere() {
        return thingsWhere;
    }

    public void setThingsWhere(String thingsWhere) {
        this.thingsWhere = thingsWhere;
    }

    public String getThingsWhen() {
        return thingsWhen;
    }

    public void setThingsWhen(String thingsWhen) {
        this.thingsWhen = thingsWhen;
    }
}
