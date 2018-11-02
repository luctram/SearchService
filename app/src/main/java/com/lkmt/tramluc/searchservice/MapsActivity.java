package com.lkmt.tramluc.searchservice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    Button btnAddRadius;
    String serviceName, placenName,place, namePlace;
    TextView serviceName1;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitide, longitude;
    private int ProximityRadius = 1000;
    private static final int LOCATION_REQUEST =500;

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
        Intent intent = getIntent();
        serviceName = intent.getStringExtra("NameService");
        serviceName1 = (TextView) findViewById(R.id.serviceName);
        serviceName1.setText("  "+serviceName);
        placenName = checkService(serviceName);
        String[] arStr = placenName.split("/");
        place = arStr[0];
        namePlace = arStr[1];

        btnAddRadius = (Button) findViewById(R.id.btnIncre);
        btnAddRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProximityRadius += 1000;
                Toast.makeText(MapsActivity.this,"Bán kính hiện tại : "+ ProximityRadius,Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
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
    @Override
    public void onLocationChanged(Location location)
    {
        latitide = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Bạn đang ở đây");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentUserLocationMarker = mMap.addMarker(markerOptions);
        currentUserLocationMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        Log.d("123",latitide + " " + longitude);
        nearPlaces(place,latitide,longitude);
    }


    private void nearPlaces(String place, double lat, double lng){
        List<Address> addressList = null;
        MarkerOptions userMarkerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(this);
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        String url = getUrl(lat, lng, place);
        transferData[0] = mMap;
        transferData[1] = url;
        getNearbyPlaces.execute(transferData);
        Toast.makeText(this, "Đang tìm " + namePlace, Toast.LENGTH_SHORT).show();
    }

    private void getDetailPlace(String placeid){
        String url = getDetailUrl(placeid);
        String addressPlace, phonePlace, namePlace, ratingPlace, openHours, websitePlace;
        ReviewService reviewPlace;
        Boolean openNow;


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


    private String getDetailUrl (String placeId){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");

        googleURL.append("placeid="+"AIzaSyA9Vf8Gc0CbgzzbjGltlxTuzNxz7PV26zw");
        googleURL.append("&fields=name,rating,formatted_phone_number,formatted_address,opening_hours,website");
        googleURL.append("&key="+"AIzaSyA9Vf8Gc0CbgzzbjGltlxTuzNxz7PV26zw");

        Log.d("OK123",""+googleURL);
        return googleURL.toString();
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

