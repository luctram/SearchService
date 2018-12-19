package com.lkmt.tramluc.searchservice.Realm;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lkmt.tramluc.searchservice.MainActivity;
import com.lkmt.tramluc.searchservice.MainActivityDelegate;
import com.lkmt.tramluc.searchservice.MapsActivity;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.Opening_Hours;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.ResultDetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.ResultDetailPlaceRealm;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.Reviews;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ServicesDB {
    public static ArrayList<ResultDetailPlace> getDetailPlace(LatLng currentPo, String type, Integer r){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ResultDetailPlaceRealm> result = realm.where(ResultDetailPlaceRealm.class).equalTo("type",type).findAll();
        ArrayList<ResultDetailPlace> finalResult = new ArrayList<>();
        for (ResultDetailPlaceRealm element:result) {
            if (element.latLng != null) {
                ResultDetailPlace item = new ResultDetailPlace();
                double x = currentPo.latitude;
                double y = currentPo.longitude;
                double xx = element.latLng.latitude;
                double yy = element.latLng.longitude;
                double xxx = (x - xx) * (x - xx);
                double yyy = (y - yy) * (y - yy);
                double delta = Math.sqrt(xxx + yyy);
                Log.d("delta", "getDetailPlace: " + delta*100000);
                if (Math.round(delta * 100000) <= r) {
                    item.setDataFromRealm(element);
                    finalResult.add(item);
                }
            }
        }
        return finalResult;
    }
    public static void addToRealm(MainActivityDelegate callback){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(realm1 -> realm.deleteAll()); // sync
         }
        finally { // chac chan chay
            if (realm != null) {
                realm.close();
            }
            getDetailPlaceFromFireBase(callback);
        }
    }
    public static void getDetailPlaceFromFireBase(MainActivityDelegate callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Services");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()) {
                    for (DataSnapshot dataNested : data.getChildren()) {
                        if (MapsActivity.network) {
                            ResultDetailPlace detail = dataNested.getValue(ResultDetailPlace.class);
                            addDetailPlace(detail);
                        }else{
                            callback.stopSpinning(false);
                            return;
                        }
                    }
                }
                callback.stopSpinning(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.stopSpinning(false);
            }
        });
    }
    public static void addDetailPlace(ResultDetailPlace input) {
        if (input != null) {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.executeTransaction(realm1 -> {
                    ResultDetailPlaceRealm result = realm.createObject(ResultDetailPlaceRealm.class);
                    result.setData(input, realm);
                    Log.d("OKNA", "addDetailPlace: " + result.formatted_address);
                });
            }
            finally {
                if (realm != null) {
                    realm.close();
                }
            }
//            if (input != null) {
//                result = realm.createObject(ResultDetailPlaceRealm.class);
//                result.latLng = input.latLng;
//                result.formatted_address = input.formatted_address;
//                result.formatted_phone_number = input.formatted_phone_number;
//                result.website = input.website;
//                result.name = input.name;
//                result.rating = input.rating;
//                RealmList<Reviews> reviews = new RealmList<>();
//                if (input.reviews != null) {
//                    for (Reviews review : input.reviews) {
//                        if (review != null) {
//                            Reviews reviewRealm = realm.createObject(Reviews.class);
//                            if (review.author_name != null) {
//                                reviewRealm.author_name = review.author_name;
//                            }
//                            if (review.profile_photo_url != null) {
//                                reviewRealm.profile_photo_url = review.profile_photo_url;
//                            }
//                            Float number = review.rating;
//                            if (number != null) {
//                                reviewRealm.rating = review.rating;
//                            }
//                            if (review.relative_time_description != null) {
//                                reviewRealm.relative_time_description = review.relative_time_description;
//                            }
//                            if (review.text != null) {
//                                reviewRealm.text = review.text;
//                            }
//                            reviews.add(reviewRealm);
//                        }
//                    }
//                    if (!reviews.isEmpty()) {
//                        result.reviews = reviews;
//                    }
//                }
//
//                Opening_Hours opening_hours;
//                if (input.opening_hours != null) {
//                    opening_hours = realm.createObject(Opening_Hours.class);
//                    opening_hours.weekday_text = input.opening_hours.weekday_text;
//                    opening_hours.open_now = input.opening_hours.open_now;
//                    result.opening_hours = opening_hours;
//                }
//            }
        }
    }

}
