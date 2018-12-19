package com.lkmt.tramluc.searchservice.ModelDetailPlace;

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
}
