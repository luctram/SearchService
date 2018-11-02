package com.lkmt.tramluc.searchservice;

public class TypeServices {
    public String service;
    public String serviceName;
    public String serviceId; // auto từ firebase

    public TypeServices() {
    }

    public TypeServices(String service, String serviceName, String serviceId) {
        this.service = service;
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }

    public TypeServices(String service, String serviceName) {
        this.service = service;
        this.serviceName = serviceName;
    }
}
