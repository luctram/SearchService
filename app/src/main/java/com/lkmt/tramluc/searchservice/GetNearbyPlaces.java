package com.lkmt.tramluc.searchservice;

import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetNearbyPlaces extends AsyncTask<Object,Void,String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    private Map<String, Integer> mMarkers = new HashMap<String, Integer>();
    private List<DetailPlace> mData = new ArrayList<DetailPlace>();
    public CallBackMap callback;
    public void setCallBack(CallBackMap callback){
        this.callback = callback;
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


    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList) throws IOException
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

            mData.add(getDetailPlace(place_id+""));
            Marker marker = mMap.addMarker(markerOptions);

            mMarkers.put(marker.getId(),i);

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker){
                        int i =   mMarkers.get(marker.getId());
                        DetailPlace data = mData.get(i);
                        callback.notifyViewStatus(data);
                    return false;
            }
        });
    }

    private DetailPlace getDetailPlace(String placeid) throws IOException{
        String url = getDetailUrl(placeid);
        ObjectMapper mapper = new ObjectMapper();
        DetailPlace data = null;
        try {
            data = mapper.readValue(new URL(url), DetailPlace.class);
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

        Log.d("OK123",""+googleURL);
        return googleURL.toString();
    }
}
