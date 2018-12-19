package com.lkmt.tramluc.searchservice;

import android.Manifest;
<<<<<<< HEAD
=======
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
<<<<<<< HEAD
=======
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.CallBackMap;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDirection.DirectionsParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

<<<<<<< HEAD
public class MapsActivity extends FragmentActivity implements CallBackMap, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
=======
import io.realm.Realm;

import static com.lkmt.tramluc.searchservice.Realm.ServicesDB.getDetailPlaceFromFireBase;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, PlaceSelectionListener, CallBackMap, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929


    Button btnAddRadius;
    String serviceName, placenName,place, namePlace;
    TextView serviceName1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LatLng lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitide, longitude;
    private LatLng latLng;
    private Boolean directing = false;
    private LatLng desLocation;
    private DetailPlace placeData;
    private int ProximityRadius = 1000;
    private static final int LOCATION_REQUEST =500;
    private List<Polyline> polylinePaths = new ArrayList<>(); // add line of direction
<<<<<<< HEAD
    TextView detailName = null, detailOpenNow=null, detailKm=null,detailAddress=null; //MapsActivity
    TextView tab_txtRating =null, tab_txtKm=null, tab_txtHour =null,tab_txtAddress=null, tab_txtPhone=null,tab_txtWebsite=null; //tabhost_detail
    TextView txtplaceName=null; // activityDetailPlace
    Button btnGo, btnGoDetail, btnShowDetail;
    RatingBar rat;
    RelativeLayout detailTable;
=======
    private List<List<HashMap<String, String>>> mRoutes = new ArrayList<>();
    private  DirectionsParser directionsParser = new DirectionsParser();
    private ArrayList<MarkerOptions> listMarkers;
    TextView detailName = null, detailOpenNow=null, detailKm=null,detailAddress=null; //MapsActivity
    Button btnIncreRadius,btnDecreRadius ,btnGo, btnGoDetail, btnShowDetail, btnOtherPlaceSearch;
    Button btnDown;
    RatingBar rat;
    RelativeLayout detailTable;
    LinearLayout layoutmenu;
    ToggleButton toggleButton;
    Boolean toggle = false;
    PlaceAutocompleteFragment autocompleteFragment;

    public DetailDirection detailDirection;
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUp();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        serviceName = intent.getStringExtra("NameService");
        serviceName1 = (TextView) findViewById(R.id.txtplaceName);
        serviceName1.setText("  "+serviceName);
        placenName = checkService(serviceName);
        String[] arStr = placenName.split("/");
        place = arStr[0];
        namePlace = arStr[1];


        btnAddRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProximityRadius += 1000;
                Toast.makeText(MapsActivity.this,"Bán kính hiện tại : "+ ProximityRadius,Toast.LENGTH_LONG).show();
            }
        });

//        btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnGoDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setOnMapLongClickListener(this);
            mMap.setMyLocationEnabled(true);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
    }

    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Từ chối cấp quyền", Toast.LENGTH_SHORT).show();
                }
                return;

            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    mMap.setMyLocationEnabled(true);

                }
                return;
        }
    }


    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
<<<<<<< HEAD
    @Override
    public void onLocationChanged(Location location)
    {
        latitide = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
=======

    private void setUp(){
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        layoutmenu = (LinearLayout) findViewById(R.id.layoutmenu);
        btnOtherPlaceSearch = (Button) findViewById(R.id.btnOtherPlaceSearch);
        detailTable =(RelativeLayout) findViewById(R.id.detailTable);
        btnIncreRadius = (Button) findViewById(R.id.btnIncre);
        btnDecreRadius =(Button) findViewById(R.id.btnDecre);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnGoDetail = (Button) findViewById(R.id.btnGoDetail);
        btnShowDetail = (Button) findViewById(R.id.btnShowDetail);
        detailName = (TextView) findViewById(R.id.detail_Name);
        detailAddress = (TextView) findViewById(R.id.detail_Address);
        detailOpenNow = (TextView) findViewById(R.id.detail_OpenNow);
        detailKm = (TextView) findViewById(R.id.detail_km);
        rat = (RatingBar) findViewById(R.id.rat);

        btnIncreRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggle && isNetworkConnected()) {
                    ProximityRadius += 1000;
                    Toast.makeText(MapsActivity.this, "Bán kính hiện tại : " + ProximityRadius, Toast.LENGTH_LONG).show();
                    nearPlaces(place, latitude, longitude, ProximityRadius);
                }
            }
        });

        btnDecreRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!toggle && isNetworkConnected()) {
                    if (ProximityRadius >= 2000) {
                        ProximityRadius -= 1000;
                        Toast.makeText(MapsActivity.this, "Bán kính hiện tại : " + ProximityRadius, Toast.LENGTH_LONG).show();
                        nearPlaces(place, latitude, longitude, ProximityRadius);
                    } else {
                        Toast.makeText(MapsActivity.this, "Không thể giảm bán kính tìm kiếm", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggle && isNetworkConnected()) {
                    DrawRoutes(latLng, desLocation);
                }
            }
        });

        btnShowDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapsActivity.this, ShowDetailPlaceActivity.class);
                intent.putExtra("DataPlace",placeData);
                intent.putExtra("dataHour",directionsParser.duration.getText());
                intent.putExtra("dataKm",directionsParser.distance.getText());
                startActivity(intent);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directing && !toggle && isNetworkConnected()) {
                    mMap.clear();
                    detailTable.setVisibility(View.INVISIBLE);
                    getMarker(lastLocation);
                    directing = false;
                }else{
                    detailTable.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOtherPlaceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!toggle && isNetworkConnected()) {
                    layoutmenu.setVisibility(View.INVISIBLE);
                    autocompleteFragment.getView().setVisibility(View.VISIBLE);
                }
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle = true;
            }
        });
    }

//    public void getAnotherAddress(String otherPlace){
//        List<Address> addressList =null;
//        if(otherPlace.trim() != ""){
//            Geocoder geocoder = new Geocoder(this);
//
//            try {
//                addressList = geocoder.getFromLocationName(otherPlace,1);
//
//                Log.d("CHECK123", addressList.size() +"");
//                if(addressList.size() == 0){
//                    addressList = geocoder.getFromLocationName(otherPlace,1);
//                }
//                if (addressList.size() >0){
//                    Address add = addressList.get(0);
//                    Log.d("CHECK1234", add.getLatitude() + "  " + add.getLongitude());
//                    LatLng latLngPlace = new LatLng(add.getLatitude(),add.getLongitude());
//                    MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLngPlace);
//                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                        mMap.addMarker(markerOptions);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngPlace));
//                        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
//
//                    nearPlaces(place, latLngPlace.latitude,latLngPlace.longitude,ProximityRadius);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (!toggle && isNetworkConnected()) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            getMarker(latLng);
//        Realm realm = Realm.getDefaultInstance();
//        ArrayList<ResultDetailPlace> list = ServicesDB.getDetailPlace(new LatLng(location.getLatitude(),location.getLongitude()),"restaurant", 2.0, realm);
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
        }
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                latLng, 15);
        mMap.moveCamera(camera);
    }

    private void getMarker(LatLng location){
        latitude = location.latitude;
        longitude = location.longitude;
        latLng = location;
        lastLocation = latLng;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Bạn đang ở đây");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentUserLocationMarker = mMap.addMarker(markerOptions);
        currentUserLocationMarker.showInfoWindow();
         //Store these lat lng values somewhere. These should be constant.

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
<<<<<<< HEAD
        Log.d("CHECK123",latitide + " " + longitude);
        nearPlaces(place,latitide,longitude);
=======
        if (!toggle && isNetworkConnected()) {
            nearPlaces(place, latitude, longitude, ProximityRadius);
        }
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

<<<<<<< HEAD

    private void nearPlaces(String place, double lat, double lng){
=======
        return cm.getActiveNetworkInfo() != null;
    }
    private void nearPlaces(String place, double lat, double lng, int ProximityRadius){
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
        List<Address> addressList = null;
        MarkerOptions userMarkerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(this);
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.setCallBack(this);
        String url = getUrl(lat, lng, place);
        transferData[0] = mMap;
        transferData[1] = url;
        getNearbyPlaces.execute(transferData);
        Toast.makeText(this, "Đang tìm " + namePlace, Toast.LENGTH_SHORT).show();
    }

    private void setUp(){
        detailTable =(RelativeLayout) findViewById(R.id.detailTable);
        btnAddRadius = (Button) findViewById(R.id.btnIncre);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnGoDetail = (Button) findViewById(R.id.btnGoDetail);
        btnShowDetail = (Button) findViewById(R.id.btnShowDetail);
        detailName = (TextView) findViewById(R.id.detail_Name);
        detailAddress = (TextView) findViewById(R.id.detail_Address);
        detailOpenNow = (TextView) findViewById(R.id.detail_OpenNow);
        detailKm = (TextView) findViewById(R.id.detail_km);
        rat = (RatingBar) findViewById(R.id.rat);

        tab_txtRating = (TextView) findViewById(R.id.tab_txtRating);
        tab_txtKm=(TextView) findViewById(R.id.tab_txtKm);
        tab_txtHour =(TextView) findViewById(R.id.tab_txtHour);
        tab_txtAddress=(TextView) findViewById(R.id .tab_txtAddress);
        tab_txtPhone= (TextView) findViewById(R.id.tab_txtPhone);
        tab_txtWebsite=(TextView) findViewById(R.id.tab_txtWebsite);

        txtplaceName = (TextView) findViewById(R.id.txtplaceName);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirection(latLng, desLocation);
            }
        });
        btnShowDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ShowDetailPlaceActivity.class);
                intent.putExtra("DataPlace",placeData);
                startActivity(intent);
            }
        });
    }
    private String getUrl(double latitide, double longitude, String nearbyPlace)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&types=" + nearbyPlace);
        googleURL.append("&language=vi");
        googleURL.append("&key="+"AIzaSyA9Vf8Gc0CbgzzbjGltlxTuzNxz7PV26zw");

        return googleURL.toString();
    }

    @Override
    public void notifyViewStatus(final DetailPlace data) {
        if (data == null) return;

        detailTable.setVisibility(View.VISIBLE); // Hien thi detail
        detailName.setText(data.result.name);
        detailAddress.setText(data.result.formatted_address);
        if (data.result.opening_hours != null) {
            if (data.result.opening_hours.open_now != null) {
                detailOpenNow.setVisibility(View.VISIBLE);
                if (data.result.opening_hours.open_now == true) {
                    detailOpenNow.setText("Đang mở cửa");
                } else {
                    detailOpenNow.setText("Đã đóng cửa");
                }
            } else {
                detailOpenNow.setVisibility(View.INVISIBLE);
            }
        }
<<<<<<< HEAD

        rat.setRating(data.result.rating);
        desLocation = data.result.latLng;
=======
        if (data.result != null && data.result.rating != null) {
            rat.setRating(data.result.rating);
            if (data.result.latLng != null && data.result.latLng.latitude != null) {
                desLocation = data.result.latLng.getLatLng();
            }
        }
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
        placeData = data;
//        detailKm = ;




        //        ta = (TextView) findViewById(R.id.detail_OpenHour);
//        tab_txtPhone.setText(data.result.formatted_phone_number);
//        tab_txtRating.setText(data.result.rating.toString());
////        tab_txtHour = (TextView) findViewById(R.id.detail_hours);
//        tab_txtAddress.setText(data.result.formatted_address);
//        tab_txtWebsite.setText(data.result.website);
////        tab_txtKm =


//        txtplaceName.setText(data.result.name);






    }

    public void getDirection(LatLng origin, LatLng dest){
        String url = getRequestUrl(origin,dest);
        System.out.println(url);
        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);
    }
<<<<<<< HEAD
=======
    public Boolean DrawRoutes(LatLng origin, LatLng dest){
        mMap.clear();

        MarkerOptions markerOrigin = new MarkerOptions();
        markerOrigin.position(origin);
        markerOrigin.title("Bạn đang ở đây");
        markerOrigin.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        MarkerOptions markerDest = new MarkerOptions();
        markerDest.position(dest);
        markerDest.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mMap.addMarker(markerOrigin);
        mMap.addMarker(markerDest);
        if (mRoutes.isEmpty()){return false;}
        ArrayList points = null;
        polylinePaths = new ArrayList<>();
        PolylineOptions polylineOptions =null;

        polylinePaths.clear();
        for (List<HashMap<String,String>> path :mRoutes){
            points =new ArrayList();
            polylineOptions = new PolylineOptions();
            for (HashMap<String,String> point: path){
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                points.add(new LatLng(lat,lng));
            }
            polylineOptions.addAll(points);
            polylineOptions.width(13);
            polylineOptions.color(R.color.colorBlue);
            polylineOptions.geodesic(true);
        }

        polylinePaths.add(mMap.addPolyline(polylineOptions));
        mRoutes.clear();
        directing = true;
        return true;
    }
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929

    private String getRequestUrl(LatLng origin, LatLng dest){
        String str_origin = "origin=" + origin.latitude +"," + origin.longitude;
        String str_dest = "destination=" +dest.latitude +"," +dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
<<<<<<< HEAD
        String key = "key=AIzaSyAgfFbBLZ-XfwSwBgZ1ztkRd2R3JLq03Kc";
        String param = str_origin +"&" +str_dest +"&" +sensor +"&" +mode +"&" +key;
//        String param = str_origin +"&" +str_dest +"&" +key;
=======
        String key = "key=AIzaSyC24CZtkc8CSjV8uyNjKqJtlc7pqGuedg4";
        String lang ="&language=vi";
        String param = str_origin +"&" +str_dest +"&" +sensor +"&" +lang+"&"+mode +"&" +key;
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
        String output ="json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" +param;
        Log.d("OK1234",url);
        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString ="";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer =new StringBuffer();
            String line ="";
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

<<<<<<< HEAD
=======
    @Override
    public void onPlaceSelected(Place place1) {
        mMap.clear();
        autocompleteFragment.getView().setVisibility(View.INVISIBLE);
        layoutmenu.setVisibility(View.VISIBLE);
        LatLng latLngPlace = place1.getLatLng();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLngPlace);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions); //Store these lat lng values somewhere. These should be constant.
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                latLngPlace, 15);
        mMap.moveCamera(location);
        lastLocation = latLngPlace;
        nearPlaces(place, latLngPlace.latitude, latLngPlace.longitude,ProximityRadius);
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (!toggle && isNetworkConnected()){
            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions); //Store these lat lng values somewhere. These should be constant.
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    latLng, 15);
            mMap.moveCamera(location);
            lastLocation = latLng;
            nearPlaces(place, latLng.latitude, latLng.longitude,ProximityRadius);

        }
    }

>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
    public class TaskRequestDirections extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString ="";
            try{
                responseString= requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String,Void,List<List<HashMap<String,String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject =null;
            List<List<HashMap<String,String>>> routes = null;

            try{
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
<<<<<<< HEAD
            ArrayList points = null;
            polylinePaths = new ArrayList<>();
            PolylineOptions polylineOptions =null;

            polylinePaths.clear();
            for (List<HashMap<String,String>> path :lists){
                points =new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String,String> point: path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    points.add(new LatLng(lat,lng));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(R.color.background_floating_material_dark);
                polylineOptions.geodesic(true);
            }
=======
            mRoutes = lists;
            detailKm.setText((directionsParser.duration != null)?directionsParser.duration.getText():"" + " | " + ((directionsParser.duration != null)?directionsParser.distance.getText():""));
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }


    }

    private String checkService(String serviceName) {
        String place = "";
        switch (serviceName) {
            case "ATM": {
                place = "atm";
                namePlace ="ATM";
                break;
            }

            case "Khu du lịch": {
                place = "amusement_park";//art_gallery, campground, museum, park, zoo
                namePlace = "khu du lịch";
                break;
            }

            case "Quán ăn": {
                place = "restaurant";
                namePlace ="quán ăn";
                break;
            }

            case "Cafe/Kem": {
                place = "cafe";
                namePlace ="cafe/kem";
                break;
            }

            case "Cửa hàng tiện lợi/Tạp hóa": {
                place = "convenience_store"; //department_store
                namePlace ="cửa hàng tiện lợi/tạp hóa";
                break;
            }

            case "Khách sạn/Nhà nghỉ": {
                place = "hotel";
                namePlace ="khách sạn/nhà nghỉ";
                break;
            }

            case "Quán bar": {
                place = "bar"; //night_club
                namePlace ="quán bar";
                break;
            }

            case "Trung tâm thương mại": {
                place = "shopping_mall";
                namePlace ="trung tâm thương mại";
                break;
            }

            case "Siêu thị": {
                place = "supermarket";
                namePlace ="siêu thị";
                break;
            }

            case "Trạm xăng": {
                place = "gas_station";
                namePlace ="trạm xăng";
                break;
            }

            default: {
                break;
            }
        }
        return place+"/"+namePlace;
    }

}

