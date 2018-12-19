package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import java.io.Serializable;
import java.util.ArrayList;

public class Opening_Hours implements Serializable {
    public Boolean open_now = false;
    public ArrayList<String> weekday_text = new ArrayList<>();

}
