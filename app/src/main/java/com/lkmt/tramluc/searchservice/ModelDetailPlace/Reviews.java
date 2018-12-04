package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class Reviews implements Serializable {
    public String authorName = "";
    public String reviewTxt = "";
    public String time = "";
    public int reviewRating = 0;
    public String linkImg = "";

    public Reviews(){}
    public Reviews (String authorName,String reviewTxt,String time, int reviewRating, String linkImg){
        this.authorName = authorName;
        this.reviewRating = reviewRating;
        this.reviewTxt = reviewTxt;
        this.time =time;
        this.linkImg = linkImg;
    }

    public String getAuthorName(){return authorName;}

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public String getReviewTxt() {
        return reviewTxt;
    }

    public void setReviewTxt(String reviewTxt) {
        this.reviewTxt = reviewTxt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
}
