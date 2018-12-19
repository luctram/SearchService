package com.lkmt.tramluc.searchservice.ModelDetailPlace;

<<<<<<< HEAD
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class ResultDetailPlace implements Serializable {
    public String formatted_address = "";
    public String formatted_phone_number = "";
    public String name = "";
    public float rating = 0;
    public String website = "";
    public Opening_Hours opening_hours = null;
    public LatLng latLng = new LatLng(0,0);
=======
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.lang.String;
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
            a.add(new String(item));
        }
        return a;
    }
    private void castToArrayListReviews(RealmList<Reviews> list){
        if (list == null) return;
        reviews = new ArrayList<Reviews>();
        for (Reviews item:list){
            reviews.add(new Reviews(item));
        }
    }
    public void setDataFromRealm(ResultDetailPlaceRealm data){
        formatted_address = new String(data.formatted_address);
        formatted_phone_number = new String(data.formatted_phone_number);
        name = new String(data.name);
        rating = new Float(data.rating);
        website = new String(data.website);
        if (data.opening_hours != null) {
            opening_hours = new Opening_Hours();
            opening_hours.open_now = new Boolean(data.opening_hours.open_now);
            opening_hours.weekday_text = castToArrayListString(data.opening_hours.weekday_text);
        }
        latLng = new LatLngg(data.latLng);
        castToArrayListReviews(data.reviews);
        type = new String(data.type);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public ResultDetailPlace(){}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(new String(formatted_address));
        dest.writeString(new String(formatted_phone_number));
        dest.writeString(new String(name));
        dest.writeFloat(new Float(rating));
        dest.writeString(new String(website));
        dest.writeParcelable(opening_hours, flags);
        dest.writeDouble(latLng.latitude);
        dest.writeDouble(latLng.longitude);
        dest.writeByte((byte)(reviews!=null?1:0));
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
        this.latLng = new LatLngg();
        this.latLng.latitude = lati;
        this.latLng.longitude = longi;
        reviews = new ArrayList<>();
        Byte a = in.readByte();
        if (a == 1) {
            in.readTypedList(reviews, Reviews.CREATOR);
        }
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
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
}
