package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.graphics.Movie;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import javax.xml.transform.Result;


@JsonIgnoreProperties
public class ResultDetailPlace implements Parcelable {
    public String formatted_address = "";
    public String formatted_phone_number = "";
    public String name = "";
    public float rating = 0;
    public String website = "";
    public Opening_Hours opening_hours = null;
    public LatLng latLng = new LatLng(0,0);

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
        this.latLng = new LatLng(lati,longi);
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

