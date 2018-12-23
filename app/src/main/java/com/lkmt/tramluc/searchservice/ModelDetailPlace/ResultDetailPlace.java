package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

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
// do tuij nay chuaw khowri taoj
    //toCopy là hàm của Parcelable
    // lỗi đó nghĩa là WriteToParcel có 1 thằng dùng lệnh chứa tham số null
    //DetailPlace.writeToParcel nghĩa là thằng Detail cũng chưa fĩ lỗi này
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
        formatted_address = new String(data.formatted_address != null?data.formatted_address:"");
        formatted_phone_number = new String(data.formatted_phone_number != null?data.formatted_phone_number:"");
        name = new String(data.name != null ? data.name:data.name);
        rating = new Float(data.rating != null ? data.rating:0);
        website = new String(data.website != null ? data.website:"");
        if (data.opening_hours != null) {
            opening_hours = new Opening_Hours();
            opening_hours.open_now = new Boolean(data.opening_hours.open_now != null ? data.opening_hours.open_now : false);
            opening_hours.weekday_text = castToArrayListString(data.opening_hours.weekday_text != null ? data.opening_hours.weekday_text:new RealmList<>());
        }
        latLng = new LatLngg(data.latLng != null ? data.latLng : new LatLngg());
        castToArrayListReviews(data.reviews != null ? data.reviews : new RealmList<>());
        type = new String(data.type != null ? data.type : "");
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public ResultDetailPlace(){}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(new String(formatted_address!=null?formatted_address:""));
        dest.writeString(new String(formatted_phone_number!=null?formatted_phone_number:""));
        dest.writeString(new String(name!=null?name:""));
        dest.writeFloat(new Float(rating!=null?rating:0.0));

        dest.writeString(new String(website!=null?website:""));
        dest.writeParcelable(opening_hours!=null?opening_hours:new Opening_Hours(), flags);
        dest.writeDouble(latLng!=null?latLng.latitude:0);
        dest.writeDouble(latLng!=null?latLng.longitude:0);
        dest.writeByte((byte)(reviews!=null?1:0));
        dest.writeTypedList(reviews!=null?reviews:new ArrayList<>());
        dest.writeString(type!=null?type:"");
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
}

