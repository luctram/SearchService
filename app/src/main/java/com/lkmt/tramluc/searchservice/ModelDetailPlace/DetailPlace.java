package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.lkmt.tramluc.searchservice.ModelDirection.DetailDirection;

import javax.xml.transform.Result;
import io.realm.Realm;
import io.realm.RealmObject;

@JsonIgnoreProperties
public class DetailPlace implements Parcelable  {
        //public ArrayList<Object> html_attributions;
    public ResultDetailPlace result;
    public String status = "";

    @Override
    public int describeContents() {
        return 0;
    }
    @JsonCreator
    public DetailPlace(@JsonProperty("result") ResultDetailPlace result, @JsonProperty("status") String status)
    {
        this.result = result;
        this.status = status;
    }
    public DetailPlace(){}

    // Storing the Movie data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(result,flags);
        dest.writeString(status);
    }

    // A constructor that initializes the Movie object
    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private DetailPlace(Parcel in){
        this.result = in.readParcelable(ResultDetailPlace.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<DetailPlace> CREATOR = new Parcelable.Creator<DetailPlace>() {
        @Override
        public DetailPlace createFromParcel(Parcel source) {
            return new DetailPlace(source);
        }

        @Override
        public DetailPlace[] newArray(int size) {
            return new DetailPlace[size];
        }
    };
}

