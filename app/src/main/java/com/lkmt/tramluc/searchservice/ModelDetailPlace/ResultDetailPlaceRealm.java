package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;


@JsonIgnoreProperties
public class ResultDetailPlaceRealm extends RealmObject {
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

}

