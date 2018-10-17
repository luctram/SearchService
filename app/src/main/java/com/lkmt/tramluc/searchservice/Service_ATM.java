package com.lkmt.tramluc.searchservice;

public class Service_ATM extends DetailServices{

    public Service_ATM() {
    }

    public Service_ATM(String serviceName, double lat, double lng, String phone, String address, int allRate, ReviewService review) {
        super(serviceName, lat, lng, phone, address, allRate, review);
    }
}
