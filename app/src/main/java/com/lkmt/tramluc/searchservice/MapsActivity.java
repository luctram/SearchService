package com.lkmt.tramluc.searchservice;

import android.Manifest;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
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
import com.lkmt.tramluc.searchservice.ModelDetailPlace.ResultDetailPlace;
import com.lkmt.tramluc.searchservice.ModelDirection.DetailDirection;
import com.lkmt.tramluc.searchservice.ModelDirection.DirectionsParser;
import com.lkmt.tramluc.searchservice.ModelDirection.Distance;
import com.lkmt.tramluc.searchservice.ModelDirection.Duration;
import com.lkmt.tramluc.searchservice.ModelDirection.StepsDirection;
import com.lkmt.tramluc.searchservice.ModelDirection.StepsDirectionAdapter;
import com.lkmt.tramluc.searchservice.Realm.ServicesDB;

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

import io.realm.Realm;

import static com.lkmt.tramluc.searchservice.Realm.ServicesDB.getDetailPlaceFromFireBase;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, PlaceSelectionListener, CallBackMap, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    String serviceName, placenName,place, namePlace;
    TextView serviceName1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LatLng lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitude, longitude;
    private LatLng latLng;
    public static Boolean network = true;
    private Boolean directing = false;
    private LatLng desLocation;
    private DetailPlace placeData;
    private int ProximityRadius = 1000;
    private int Radius = 1;
    private static final int LOCATION_REQUEST =500;
    private List<Polyline> polylinePaths = new ArrayList<>(); // add line of direction
    private List<List<HashMap<String, String>>> mRoutes = new ArrayList<>();
    private  DirectionsParser directionsParser = new DirectionsParser();
    private ArrayList<MarkerOptions> listMarkers;
    TextView detailName = null, detailOpenNow=null, detailKm=null,detailAddress=null; //MapsActivity
    Button btnIncreRadius,btnDecreRadius ,btnGo, btnGoDetail, btnShowDetail, btnOtherPlaceSearch;
    Button btnDown, btnCancelDirecion;
    RatingBar rat;
    RelativeLayout detailTable, directionTable;
    LinearLayout layoutmenu;
    ToggleButton toggleButton;
    Boolean toggle = false;
    PlaceAutocompleteFragment autocompleteFragment;
    ArrayList<StepsDirection> arrDirection;
    StepsDirectionAdapter adapterDirection;
    ListView listViewDirection;

    public DetailDirection detailDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.getView().setVisibility(View.INVISIBLE);

        setUp();

        Intent intent = getIntent();
        serviceName = intent.getStringExtra("NameService");
        serviceName1 = (TextView) findViewById(R.id.txtplaceName);
        serviceName1.setText("  "+serviceName);
        placenName = checkService(serviceName);
        String[] arStr = placenName.split("/");
        place = arStr[0];
        namePlace = arStr[1];
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

    private void setUp(){
        directionTable = (RelativeLayout) findViewById(R.id.directionTable);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        layoutmenu = (LinearLayout) findViewById(R.id.layoutmenu);
        btnOtherPlaceSearch = (Button) findViewById(R.id.btnOtherPlaceSearch);
        detailTable =(RelativeLayout) findViewById(R.id.detailTable);
        btnIncreRadius = (Button) findViewById(R.id.btnIncre);
        btnDecreRadius =(Button) findViewById(R.id.btnDecre);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnCancelDirecion = (Button) findViewById(R.id.btnCancelDirection);
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
                    getMarker(lastLocation);
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
                        getMarker(lastLocation);
                    } else {
                        Toast.makeText(MapsActivity.this, "Không thể giảm bán kính tìm kiếm", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network) {
                    detailTable.setVisibility(View.INVISIBLE);
                    directionTable.setVisibility(View.VISIBLE);
                    DrawRoutes(latLng, desLocation);
                    showStepsDirection();
                }else{
                    Toast.makeText(getBaseContext(),"Không có kết nối mạng", Toast.LENGTH_LONG).show();
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
                if (directing) {
                    mMap.clear();
                    detailTable.setVisibility(View.INVISIBLE);
                    getMarker(lastLocation);
                    directing = false;
                }else{
                    detailTable.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCancelDirecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (directing) {
                    mMap.clear();
                    directionTable.setVisibility(View.INVISIBLE);
                    getMarker(lastLocation);
                    directing = false;
                }else{
                    directionTable.setVisibility(View.INVISIBLE);
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
                toggle = !toggle;
                if (toggle) {
                    btnGo.setVisibility(View.INVISIBLE);
                }else{
                    btnGo.setVisibility(View.VISIBLE);
                }
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

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    //        Realm realm = Realm.getDefaultInstance();
//        ArrayList<ResultDetailPlace> list = ServicesDB.getDetailPlace(new LatLng(location.getLatitude(),location.getLongitude()),"restaurant", 2.0, realm);
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(
                latLng, 15);
        mMap.moveCamera(camera);
        getMarker(latLng);
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
        if (!toggle && network) {
            nearPlaces(place, latitude, longitude, ProximityRadius);
            btnGo.setVisibility(View.VISIBLE);
        }else{
            btnGo.setVisibility(View.INVISIBLE);
            GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
            getNearbyPlaces.setCallBack(this, mMap);
            List<ResultDetailPlace> list = ServicesDB.getDetailPlace(lastLocation,place,ProximityRadius);
            getNearbyPlaces.DisplayNearbyPlacesOffline(list);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void nearPlaces(String place, double lat, double lng, int ProximityRadius){
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.setCallBack(this, mMap);
        String url = getUrl(lat, lng, place, ProximityRadius );
        transferData[0] = mMap;
        transferData[1] = url;
        getNearbyPlaces.execute(transferData);
//        Toast.makeText(this, "Đang tìm " + namePlace, Toast.LENGTH_SHORT).show();
    }

//json cac dia diem gan
    private String getUrl(double latitide, double longitude, String nearbyPlace, int ProximityRadius)
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
        detailName.setText(data.result.name != null ? data.result.name : "");
        detailAddress.setText(data.result.formatted_address != null ? data.result.formatted_address : "");
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
        if (data.result != null && data.result.rating != null) {
            rat.setRating(data.result.rating);
        }else{
            rat.setRating(0);
        }
        if (data.result.latLng != null && data.result.latLng.latitude != null) {
            desLocation = data.result.latLng.getLatLng();
        }
        placeData = data;
        directionsParser.duration = new Duration("",0);
        directionsParser.distance = new Distance("",0);
        if (network) {
            getDirection(latLng, desLocation);
        }else {
            detailKm.setText("");
        }
    }

    public void getDirection(LatLng origin, LatLng dest){
        String url = getRequestUrl(origin,dest);
        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);
    }
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

    //json direction
    private String getRequestUrl(LatLng origin, LatLng dest){
        String str_origin = "origin=" + origin.latitude +"," + origin.longitude;
        String str_dest = "destination=" +dest.latitude +"," +dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key=AIzaSyC24CZtkc8CSjV8uyNjKqJtlc7pqGuedg4";
        String lang ="&language=vi";
        String param = str_origin +"&" +str_dest +"&" +sensor +"&" +lang+"&"+mode +"&" +key;
        String output ="json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" +param;
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

    public void showStepsDirection(){
        listViewDirection = (ListView) findViewById(R.id.listviewDirection);
        arrDirection = new ArrayList<StepsDirection>();
        for(int i =0 ; i < directionsParser.totalStepsDirection; i++){
            arrDirection.add(new StepsDirection(directionsParser.stepsDirections.get(i).getDurationStep(),directionsParser.stepsDirections.get(i).getDistanceStep(),directionsParser.stepsDirections.get(i).getDescribe()));
        }

        adapterDirection = new StepsDirectionAdapter(this, R.layout.row_listview_stepsdirection,arrDirection);
        listViewDirection.setAdapter(adapterDirection);
    }

    @Override
    public void onPlaceSelected(Place place1) {
        mMap.clear();
        autocompleteFragment.getView().setVisibility(View.INVISIBLE);
        layoutmenu.setVisibility(View.VISIBLE);
        LatLng latLngPlace = place1.getLatLng();

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLngPlace);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        mMap.addMarker(markerOptions);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                latLngPlace, 15);
        mMap.moveCamera(location);
        getMarker(latLngPlace);
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions); //Store these lat lng values somewhere. These should be constant.
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                latLng, 15);
        mMap.moveCamera(location);
        getMarker(latLng);
    }

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
                routes = directionsParser.parse(jsonObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            mRoutes = lists;
            detailKm.setText((directionsParser.duration != null)?directionsParser.duration.getText():"" + " | " + ((directionsParser.distance != null)?directionsParser.distance.getText():""));
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
                place = "amusement_park";//|art_gallery|campground|museum|zoo
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
                place = "convenience_store"; //|department_store
                namePlace ="cửa hàng tiện lợi/tạp hóa";
                break;
            }

            case "Khách sạn/Nhà nghỉ": {
                place = "lodging";
                namePlace ="khách sạn/nhà nghỉ";
                break;
            }

            case "Quán bar": {
                place = "bar"; //|night_club
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

