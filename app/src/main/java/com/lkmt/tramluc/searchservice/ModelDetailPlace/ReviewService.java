package com.lkmt.tramluc.searchservice.ModelDetailPlace;

public class ReviewService {
    private String authorName;
    private String reviewTxt;
    private String time;
    private int reviewRating;

    public ReviewService() {
    }

    public ReviewService(String authorName, String reviewTxt, String time, int rate) {
        this.authorName = authorName;
        this.reviewTxt = reviewTxt;
        this.time = time;
        this.reviewRating = rate;
    }
}
