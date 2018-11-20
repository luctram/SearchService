package com.lkmt.tramluc.searchservice;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailPlace {
    HashMap html_attributions;
    ArrayList<DetailAPI> result;
    String status;
    public class DetailAPI{
        String formatted_address;
        String formatted_phone_number;
        String name;
        Number rating;
        String website;
        Opening_Hours opening_hours;
        LatLng latLng;
        public class Opening_Hours{
            Boolean open_now;
            ArrayList<String> weekday_text;
        }

        ArrayList<reviews> reviews;

        public class reviews{
            private String authorName;
            private String reviewTxt;
            private String time;
            private int reviewRating;
            private String linkImg;
        }
    }
}

