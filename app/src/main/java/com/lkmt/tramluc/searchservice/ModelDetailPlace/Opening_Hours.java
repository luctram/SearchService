package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

@JsonIgnoreProperties
public class Opening_Hours implements Parcelable {
    public Boolean open_now;
    public ArrayList<String> weekday_text;

    @Override
    public int describeContents() {
        return 0;
    }
    public Opening_Hours(){
        open_now = false;
        weekday_text = new ArrayList<>();
    }

    // Storing the Movie data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(open_now?1:0));
        dest.writeStringList(weekday_text!=null?weekday_text: new ArrayList<>());
    }

    // A constructor that initializes the Movie object
    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Opening_Hours(Parcel in){
        this.open_now = in.readByte() != 0;
        weekday_text = new ArrayList<>();
        in.readStringList(weekday_text);
    }

    public static final Parcelable.Creator<Opening_Hours> CREATOR = new Parcelable.Creator<Opening_Hours>() {
        @Override
        public Opening_Hours createFromParcel(Parcel source) {
            return new Opening_Hours(source);
        }

        @Override
        public Opening_Hours[] newArray(int size) {
            return new Opening_Hours[size];
        }
    };
}
