package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
@JsonIgnoreProperties
public class DetailPlace implements Serializable {
    public ArrayList<Object> html_attributions;
    public ResultDetailPlace result;
    public String status = "";
}

