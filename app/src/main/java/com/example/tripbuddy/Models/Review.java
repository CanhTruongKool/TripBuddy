package com.example.tripbuddy.Models;

public class Review {
    private String username;
    private String reviewText;
    private String reviewTime;

    public Review(String username, String reviewText, String reviewTime) {
        this.username = username;
        this.reviewText = reviewText;
        this.reviewTime = reviewTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}

