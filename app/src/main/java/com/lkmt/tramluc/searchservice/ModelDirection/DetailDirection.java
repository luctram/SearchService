package com.lkmt.tramluc.searchservice.ModelDirection;

public class DetailDirection {
    private String distance;
    private String duration;

    public DetailDirection(String distance, String duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public String getDistance(){return distance;}

    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getDuration(){return duration;}

    public void setDuration(String duration){
        this.duration = duration;
    }

}
