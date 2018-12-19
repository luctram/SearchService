package com.lkmt.tramluc.searchservice;

import com.lkmt.tramluc.searchservice.ModelDetailPlace.Reviews;

public class Services {

    public String typeServiceId;
    public String cityId;
    public String serviceId; // auto từ firebase
    public String serviceName;
    public double lat;
    public double lng;
    public String phone;
    public String address;
    public int rate;
    public Reviews review;
    public String describe;
    public int countLike;
    public String website;
//    public img


    public Services() {
    }

    public Services(String typeServiceId, String cityId, String serviceId, String serviceName, double lat, double lng, String phone, String address, int allRate, Reviews review, String describe, int countLike, String website) {
        this.typeServiceId = typeServiceId;
        this.cityId = cityId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.address = address;
        this.rate = allRate;
        this.review = review;
        this.describe = describe;
        this.countLike = countLike;
        this.website = website;
    }
}
