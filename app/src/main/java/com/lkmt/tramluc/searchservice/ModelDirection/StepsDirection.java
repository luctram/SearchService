package com.lkmt.tramluc.searchservice.ModelDirection;

public class StepsDirection {
    private Duration durationStep;
    private Distance distanceStep;
    private String describe;


    public StepsDirection(Duration durationStep, Distance distanceStep, String describe) {
        this.durationStep = durationStep;
        this.distanceStep = distanceStep;
        this.describe = describe;
    }

    public Duration getDurationStep() {
        return durationStep;
    }

    public void setDurationStep(Duration durationStep) {
        this.durationStep = durationStep;
    }

    public Distance getDistanceStep() {
        return distanceStep;
    }

    public void setDistanceStep(Distance distanceStep) {
        this.distanceStep = distanceStep;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
