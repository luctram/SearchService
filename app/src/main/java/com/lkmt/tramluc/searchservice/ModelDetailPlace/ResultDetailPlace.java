package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;


@JsonIgnoreProperties
public class ResultDetailPlace implements Parcelable {
    public String formatted_address;
    public String formatted_phone_number;
    public String name;
    public Float rating;
    public String website;
    public Opening_Hours opening_hours;
    public LatLngg latLng;
    public ArrayList<Reviews> reviews;
    public String type;

    private ArrayList<String> castToArrayListString(RealmList<String> list){
       ArrayList<String> a = new ArrayList<>();
        for (String item: list) {
            a.add(item);
        }
        return a;
    }
    private void castToArrayListReviews(RealmList<Reviews> list){
        if (list == null) return;
        reviews = new ArrayList<Reviews>();
        for (Reviews item:list){
            reviews.add(item);
        }
    }
    public void setDataFromRealm(ResultDetailPlaceRealm data){
        Realm realm = Realm.getDefaultInstance();
        formatted_address = data.formatted_address;
        formatted_phone_number = data.formatted_phone_number;
        name = data.name;
        rating = data.rating;
        website = data.website;
        if (data.opening_hours != null) {
            opening_hours = new Opening_Hours();
            opening_hours.open_now = data.opening_hours.open_now;
            opening_hours.weekday_text = castToArrayListString(data.opening_hours.weekday_text);
        }
        latLng = data.latLng;
        castToArrayListReviews(data.reviews);
        type = data.type;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public ResultDetailPlace(){}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formatted_address);
        dest.writeString(formatted_phone_number);
        dest.writeString(name);
        dest.writeFloat(rating);
        dest.writeString(website);
        dest.writeParcelable(opening_hours, flags);
        dest.writeDouble(latLng.latitude);
        dest.writeDouble(latLng.longitude);
        dest.writeTypedList(reviews);
        dest.writeString(type);
    }
    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private ResultDetailPlace(Parcel in){
        this.formatted_address = in.readString();
        this.formatted_phone_number = in.readString();
        this.name = in.readString();
        this.rating = in.readFloat();
        this.website = in.readString();
        this.opening_hours = in.readParcelable(Opening_Hours.class.getClassLoader());
        Double lati = in.readDouble();
        Double longi = in.readDouble();
        this.latLng = new LatLngg(lati,longi);
        in.readTypedList(reviews,Reviews.CREATOR);
        this.type = in.readString();

    }

    public static final Parcelable.Creator<ResultDetailPlace> CREATOR = new Parcelable.Creator<ResultDetailPlace>() {
        @Override
        public ResultDetailPlace createFromParcel(Parcel source) {
            return new ResultDetailPlace(source);
        }

        @Override
        public ResultDetailPlace[] newArray(int size) {
            return new ResultDetailPlace[size];
        }
    };
}

