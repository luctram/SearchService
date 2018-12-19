package com.lkmt.tramluc.searchservice;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.CallBackMap;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.LatLngg;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.ResultDetailPlace;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

public class GetNearbyPlaces extends AsyncTask<Object,Void,String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    private HashMap<String, Integer> mMarkers;
    private ArrayList<MarkerOptions> listMarkers;
    //private HashMap<String,LatLng> mData1;
    private List<DetailPlace> mData;
    public CallBackMap callback;
    public void setCallBack(CallBackMap callback, GoogleMap mMap){
        this.callback = callback;
        this.mMap = mMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker){

                if (mMarkers.get(marker.getId()) != null){
                    int i = mMarkers.get(marker.getId());
                    DetailPlace data = mData.get(i);
                    callback.notifyViewStatus(data);
                }
                callback.notifyViewStatus(null);
                return false;
            }
        });
    }
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
        try {
            DisplayNearbyPlaces(nearByPlacesList);
        }
        catch (IOException err){
            Log.d("IOex", "onPostExecute: "+ err.getMessage());
        }
    }

    public void DisplayNearbyPlacesOffline(List<ResultDetailPlace> nearByPlacesList){

        listMarkers = new ArrayList<>();
        mMarkers = new HashMap<String, Integer>();
        mData = new ArrayList<DetailPlace>();
        // mData1 = new HashMap<String,LatLng>();
        Services sv;
        for (int i=0; i< nearByPlacesList.size(); i++)
        {
            ResultDetailPlace data = nearByPlacesList.get(i);
            MarkerOptions markerOptions = new MarkerOptions();
            String nameOfPlace = data.name;// name
            String vicinity = data.formatted_address;// address
            LatLng latLng = data.latLng.getLatLng();
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            DetailPlace temp = new DetailPlace();
            temp.result = data;
            mData.add(temp);
            //  mData1.put(place_id,latLng);
            Marker marker = mMap.addMarker(markerOptions);
            mMarkers.put(marker.getId(),i);
        }
    }
    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList) throws IOException
    {
        listMarkers = new ArrayList<>();
        mMarkers = new HashMap<String, Integer>();
        mData = new ArrayList<DetailPlace>();
        // mData1 = new HashMap<String,LatLng>();
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

            mData.add(getDetailPlace(place_id+"", latLng));
          //  mData1.put(place_id,latLng);

            Marker marker = mMap.addMarker(markerOptions);
            mMarkers.put(marker.getId(),i);
        }

    }
    private DetailPlace getDetailPlace(String placeid, LatLng latLng) throws IOException{
        String url = getDetailUrl(placeid);
        ObjectMapper mapper = new ObjectMapper();
        DetailPlace data = null;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            data = mapper.readValue(new URL(url), DetailPlace.class);
            if (data.result != null) {
                data.result.latLng = new LatLngg();
                data.result.latLng.setLatLng(latLng);
            }
        }
        catch (MalformedURLException err){
            Log.d("ChecckERR", err.getMessage()+"");
        }
        catch (IOException err){
            Log.d("ChecckERR1", err.getMessage()+"");
        }
        return data;

    }

    private String getDetailUrl (String placeId){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");

        googleURL.append("placeid="+ placeId);
        googleURL.append("&fields=name,rating,formatted_phone_number,formatted_address,opening_hours,website,reviews");
        googleURL.append("&key="+"AIzaSyAgfFbBLZ-XfwSwBgZ1ztkRd2R3JLq03Kc");

       // Log.d("OK123",""+googleURL);
        return googleURL.toString();
    }
}
