package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class Reviews implements Parcelable {
    public String author_name = "";
    public String text = "";
    public String relative_time_description = "";
    public float rating = 0;
    public String profile_photo_url = "";

    public Reviews(){}
    public Reviews (String author_name,String text,String relative_time_description, float rating, String profile_photo_url){
        this.author_name = author_name;
        this.rating = rating;
        this.text = text;
        this.relative_time_description =relative_time_description;
        this.profile_photo_url = profile_photo_url;
    }

    public String getauthor_name(){return author_name;}

    public void setauthor_name(String author_name){
        this.author_name = author_name;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public String getrelative_time_description() {
        return relative_time_description;
    }

    public void setrelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public String getprofile_photo_url() {
        return profile_photo_url;
    }

    public void setprofile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public float getrating() {
        return rating;
    }

    public void setrating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(author_name);
        dest.writeString(relative_time_description);
        dest.writeString(text);
        dest.writeString(profile_photo_url);
        dest.writeFloat(rating);

    }
    private Reviews(Parcel in){
        this.author_name = in.readString();
        this.profile_photo_url = in.readString();
        this.relative_time_description = in.readString();
        this.text = in.readString();
        this.rating = in.readFloat();
    }


    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
