package com.lkmt.tramluc.searchservice;

public class ReviewService {
    private String authorName;
    private String reviewTxt;
    private String time;
    private int rate;

    public ReviewService() {
    }

    public ReviewService(String authorName, String reviewTxt, String time, int rate) {
        this.authorName = authorName;
        this.reviewTxt = reviewTxt;
        this.time = time;
        this.rate = rate;
    }
}
