package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.String;

import io.realm.RealmList;
import io.realm.RealmObject;

@JsonIgnoreProperties
public class Opening_HoursRealm extends RealmObject implements Parcelable {
    public Boolean open_now = false;
    public RealmList<String> weekday_text = new RealmList<>();

    @Override
    public int describeContents() {
        return 0;
    }
    public Opening_HoursRealm(){}

    // Storing the Movie data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(open_now?1:0));
        dest.writeStringList(weekday_text);
    }

    // A constructor that initializes the Movie object
    /**
     * Retrieving Movie data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Opening_HoursRealm(Parcel in){
        this.open_now = in.readByte() != 0;
        in.readStringList(weekday_text);
    }

    public static final Parcelable.Creator<Opening_HoursRealm> CREATOR = new Parcelable.Creator<Opening_HoursRealm>() {
        @Override
        public Opening_HoursRealm createFromParcel(Parcel source) {
            return new Opening_HoursRealm(source);
        }

        @Override
        public Opening_HoursRealm[] newArray(int size) {
            return new Opening_HoursRealm[size];
        }
    };
}
