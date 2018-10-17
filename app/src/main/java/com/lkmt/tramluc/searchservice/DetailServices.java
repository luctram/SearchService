package com.lkmt.tramluc.searchservice;

public class DetailServices {
    public String serviceName;
    public double lat;
    public double lng;
    public String phone;
    public String address;
    public int allRate;
    public ReviewService review;

//    public String price_level;
//    public String describe;
//    public int countLike;
//
//
//
//    public String website;

    public DetailServices() {
    }

    public DetailServices(String serviceName, double lat, double lng, String phone, String address, int allRate, ReviewService review) {
        this.serviceName = serviceName;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.address = address;
        this.allRate = allRate;
        this.review = review;
    }
}
