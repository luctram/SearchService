package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailPlace {
    public HashMap html_attributions;
    public ArrayList<DetailAPI> result;
    public String status;
    public class DetailAPI{
        public String formatted_address;
        public String formatted_phone_number;
        public String name;
        public Number rating;
        public String website;
        public Opening_Hours opening_hours;
        public LatLng latLng;
        public class Opening_Hours{
            public Boolean open_now;
            public ArrayList<String> weekday_text;
        }

        public ArrayList<reviews> reviews;

        public class reviews{
            public String authorName;
            public String reviewTxt;
            public String time;
            public int reviewRating;
            public String linkImg;
        }
    }
}

