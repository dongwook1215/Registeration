package com.example.registeration;

public class ReviewList {
    String userID;
    String mobilityName;
    String mobilityScore;
    String reviewContent;

    public ReviewList(String userID, String mobilityName, String mobilityScore, String reviewContent) {
        this.userID = userID;
        this.mobilityName = mobilityName;
        this.mobilityScore = mobilityScore;
        this.reviewContent = reviewContent;
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

    public String getMobilityScore() {
        return mobilityScore;
    }

    public void setMobilityScore(String mobilityScore) {
        this.mobilityScore = mobilityScore;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
