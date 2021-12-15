package com.pace.myteacher;

public class ReviewItem {
    private String reviewer;
    private String rating;
    private String review;

    public ReviewItem(String reviewer, String rating, String review) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
