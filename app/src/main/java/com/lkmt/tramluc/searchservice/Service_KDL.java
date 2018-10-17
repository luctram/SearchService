package com.lkmt.tramluc.searchservice;

public class Service_KDL extends DetailServices {
    public String price_level;
    public String describe;
    public int countLike;
    public String website;

    public Service_KDL() {

    }

    public Service_KDL(String serviceName, double lat, double lng, String phone, String address, int allRate, ReviewService review, String price_level, String describe, int countLike, String website) {
        super(serviceName, lat, lng, phone, address, allRate, review);
        this.price_level = price_level;
        this.describe = describe;
        this.countLike = countLike;
        this.website = website;
    }
}
