package com.lkmt.tramluc.searchservice;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    private JSONObject googlePlaceJSON;

    @Override
    protected String doInBackground(Object... objects)
    {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheURL(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s)
    {
        List<HashMap<String, String>> nearByPlacesList = null;
        DataParser dataParser = new DataParser();
        nearByPlacesList = dataParser.parse(s);

        DisplayNearbyPlaces(nearByPlacesList);
    }


    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
    {

        Services sv;
           for (int i=0; i< nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name"); // name
            String vicinity = googleNearbyPlace.get("vicinity"); // address
            String place_id = googleNearbyPlace.get("place_id");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            getDetailPlace(place_id+"");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    private void getDetailPlace(String placeid) {
        String url = getDetailUrl(placeid);
        String addressPlace, phonePlace, namePlace, ratingPlace, openHours, websitePlace;
        ReviewService reviewPlace;
        Boolean openNow;
        phonePlace="";
        try {
            if (!googlePlaceJSON.isNull("name")) { // sai ở đây, k lấy đc json để gán
                namePlace = googlePlaceJSON.getJSONObject("result").getString("name");
            }

            if (!googlePlaceJSON.isNull("formatted_phone_number")) {
                phonePlace = googlePlaceJSON.getJSONObject("result").getString("formatted_phone_number");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    Log.d("123456", "OK" + " " + googlePlaceJSON.getJSONObject("result").getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    private String getDetailUrl (String placeId){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");

        googleURL.append("placeid="+ placeId);
        googleURL.append("&fields=name,rating,formatted_phone_number,formatted_address,opening_hours,website");
        googleURL.append("&key="+"AIzaSyAgfFbBLZ-XfwSwBgZ1ztkRd2R3JLq03Kc");

        Log.d("OK123",""+googleURL);
        return googleURL.toString();
    }
}
