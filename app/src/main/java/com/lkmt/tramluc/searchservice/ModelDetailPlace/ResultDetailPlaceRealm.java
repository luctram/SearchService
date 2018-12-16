package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;


@JsonIgnoreProperties
public class ResultDetailPlaceRealm extends RealmObject implements Parcelable {
    public String formatted_address;
    public String formatted_phone_number;
    public String name;
    public Float rating;
    public String website;
    public Opening_HoursRealm opening_hours;
    public LatLngg latLng;
    public RealmList<Reviews> reviews;
    public String type;

    private RealmList<String> castToArrayListString(ArrayList<String> list){
        RealmList<String> a = new RealmList<>();
        for (String item: list) {
            a.add(item);
        }
        return a;
    }
    private  void castToArrayListReviews(ArrayList<Reviews> list){
        if (list == null) return;
        for (Reviews item:list){
            reviews.add(item);
        }
    }

    public void setData(ResultDetailPlace data, Realm realm){
        formatted_address = data.formatted_address;
        formatted_phone_number = data.formatted_phone_number;
        name = data.name;
        rating = data.rating;
        website = data.website;
        if (data.opening_hours != null) {
            opening_hours = realm.createObject(Opening_HoursRealm.class);
            opening_hours.open_now = data.opening_hours.open_now;
            opening_hours.weekday_text = castToArrayListString(data.opening_hours.weekday_text);
        }
        latLng = realm.copyToRealm(data.latLng);
        castToArrayListReviews(data.reviews);
        type = data.type;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public ResultDetailPlaceRealm(){}
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
    private ResultDetailPlaceRealm(Parcel in){
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

    public static final Parcelable.Creator<ResultDetailPlaceRealm> CREATOR = new Parcelable.Creator<ResultDetailPlaceRealm>() {
        @Override
        public ResultDetailPlaceRealm createFromParcel(Parcel source) {
            return new ResultDetailPlaceRealm(source);
        }

        @Override
        public ResultDetailPlaceRealm[] newArray(int size) {
            return new ResultDetailPlaceRealm[size];
        }
    };
}

