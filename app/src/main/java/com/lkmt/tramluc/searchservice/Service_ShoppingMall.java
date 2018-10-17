package com.lkmt.tramluc.searchservice;

public class Service_ShoppingMall extends DetailServices {
    public String describe;
    public int countLike;
    public String website;

    public Service_ShoppingMall() {
    }

    public Service_ShoppingMall(String serviceName, double lat, double lng, String phone, String address, int allRate, ReviewService review, String describe, int countLike, String website) {
        super(serviceName, lat, lng, phone, address, allRate, review);
        this.describe = describe;
        this.countLike = countLike;
        this.website = website;
    }
}
