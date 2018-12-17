package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

public class LatLngg extends RealmObject {
    public Double latitude;
    public Double longitude;
    public LatLngg(LatLngg latlng) {
        this.latitude = latlng.latitude;
        this.longitude = latlng.longitude;
    }
    public void setLatLng(LatLng latlng){
        this.latitude = latlng.latitude;
        this.longitude = latlng.longitude;
    }
    public LatLngg() {
    }

    public LatLng getLatLng(){
        return new LatLng(this.latitude,this.longitude);
    }

}
