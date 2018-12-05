package com.lkmt.tramluc.searchservice.ModelDirection;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
//import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NgocTri on 12/11/2017.
 */

public class DirectionsParser {
    /**
     * Returns a list of lists containing latitude and longitude from a JSONObject
     */
    public Distance distance;
    public Duration duration;
    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        JSONArray jsonRoutes = null;
        List<HashMap<String,String>> path = new ArrayList<HashMap<String,String>>();
        try {

            jsonRoutes = jObject.getJSONArray("routes");

            // Loop for all routes
            for (int i = 0; i < jsonRoutes.length(); i++) {
                JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

                JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                JSONArray jsonSteps = jsonLeg.getJSONArray("steps");
                JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                duration = new Duration(jsonDuration.getString("text"),jsonDuration.getInt("value"));
                for (int j = 0; j<jsonSteps.length(); j++){
                    JSONObject jsonStep = jsonSteps.getJSONObject(j);
                    Steps steps = new Steps();
                    JSONObject jStepDistance = jsonStep.getJSONObject("distance");
                    steps.distance = new Distance(jStepDistance.getString("text"),jStepDistance.getInt("value"));

                    JSONObject jStepDuration = jsonStep.getJSONObject("duration");
                    steps.duration = new Duration(jStepDuration.getString("text"),jStepDuration.getInt("value"));

                    JSONObject jStepPolyline = jsonStep.getJSONObject("polyline");
                    steps.polyline = new polyline(jStepPolyline.getString("points"));
                    List list = PolyUtil.decode(jStepPolyline.getString("points"));

                    for (int l = 0; l < list.size(); l++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                        hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                        path.add(hm);
                    }

                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        return routes;
    }


    /**
     * Method to decode polyline
     * Source : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
}