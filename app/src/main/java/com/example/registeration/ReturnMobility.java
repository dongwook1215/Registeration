package com.example.registeration;

public class ReturnMobility {
    int mobilityID;
    String userID;
    String mobilityName;
    String mobilityWhere;
    String mobilityWhen;

    public ReturnMobility(int mobilityID, String userID, String mobilityName, String mobilityWhere, String mobilityWhen) {
        this.mobilityID = mobilityID;
        this.userID = userID;
        this.mobilityName = mobilityName;
        this.mobilityWhere = mobilityWhere;
        this.mobilityWhen = mobilityWhen;
    }

    public int getMobilityID() {
        return mobilityID;
    }

    public void setMobilityID(int mobilityID) {
        this.mobilityID = mobilityID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMobilityName() {
        return mobilityName;
    }

    public void setMobilityName(String mobilityName) {
        this.mobilityName = mobilityName;
    }

    public String getMobilityWhere() {
        return mobilityWhere;
    }

    public void setMobilityWhere(String mobilityWhere) {
        this.mobilityWhere = mobilityWhere;
    }

    public String getMobilityWhen() {
        return mobilityWhen;
    }

    public void setMobilityWhen(String mobilityWhen) {
        this.mobilityWhen = mobilityWhen;
    }
}
