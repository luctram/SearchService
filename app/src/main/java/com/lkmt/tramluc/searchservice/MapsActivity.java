package com.lkmt.tramluc.searchservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    String serviceName;
    TextView serviceName1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment =(MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
         serviceName = intent.getStringExtra("ServiceName");

         serviceName1 = (TextView) findViewById(R.id.txtName);
         serviceName1.setText(serviceName+""); //sai
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map =googleMap;
        LatLng sydney = new LatLng(-33.867,151.206);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
        map.addMarker(new MarkerOptions()
                .title("Sydneys")
                .snippet("ahihi")
                .position(sydney)
        );
    }
}
