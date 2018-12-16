package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import io.realm.RealmObject;

@JsonIgnoreProperties
public class Reviews extends RealmObject implements Parcelable {
    public String author_name = "";
    public String text = "";
    public String relative_time_description = "";
    public float rating = 0;
    public String profile_photo_url = "";

    public Reviews(){}

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public void setRelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public Reviews (String author_name, String text, String relative_time_description, float rating, String profile_photo_url){
        this.author_name = author_name;
        this.text = text;
        this.relative_time_description =relative_time_description;

        this.rating = rating;
        this.profile_photo_url = profile_photo_url;
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
