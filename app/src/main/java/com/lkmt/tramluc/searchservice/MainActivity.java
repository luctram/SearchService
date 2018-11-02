package com.lkmt.tramluc.searchservice;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lkmt.tramluc.searchservice.TypeServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GoogleMap mMap;
    DatabaseReference mData;
    ListView listViewmenu;
    ArrayList<Menu> arrMenu;
    MenuAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] listCities = new String[]{"Thành phố Hồ Chí Minh","Hà Nội","Nha Trang","Vũng Tàu","Phan Thiết","Đà Lạt","Cần Thơ", "Đà Nẵng","Sa Pa"};

        mData = FirebaseDatabase.getInstance().getReference();
////
////        for(int i=0; i< listCities.length; i++){
////            mData.child("cities").push().setValue(listCities[i]);
////        }

//        DetailServices detailServices = new DetailServices("ád",0.0,9.0,"cfg","sad","ád","ád",3,1,"ád","Ad");
//        TypeServices listServices;
//        listServices = new TypeServices("atm","ATM");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("museum","Khu du lịch");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("restaurant","Quán ăn");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("cafe","Cafe/Kem");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("chtl","Cửa hàng tiện lợi/Tạp hóa");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("hotel","Khách sạn/Nhà nghỉ");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("bar","Quán bar");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("tttm","Trung tâm thương mại");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("supermarket","Siêu thị");
//        mData.child("TypeServices").push().setValue(listServices);
//
//        listServices = new TypeServices("gas_station","Trạm xăng");
//        mData.child("TypeServices").push().setValue(listServices);
//

        anhxa();
        adapter = new MenuAdapter(this,R.layout.row_listview_menu,arrMenu);
        listViewmenu.setAdapter(adapter);

        listViewmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, arrMenu.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("NameService",arrMenu.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void anhxa(){
        listViewmenu = (ListView) findViewById(R.id.listview);
        arrMenu = new ArrayList<Menu>();

        arrMenu.add(new Menu("ATM",R.mipmap.atm));
        arrMenu.add(new Menu("Khu du lịch",R.mipmap.kvc1));
        arrMenu.add(new Menu("Quán ăn",R.mipmap.quanan1));
        arrMenu.add(new Menu("Cafe/Kem",R.mipmap.cf1));
        arrMenu.add(new Menu("Cửa hàng tiện lợi/Tạp hóa",R.mipmap.chtl1));
        arrMenu.add(new Menu("Khách sạn/Nhà nghỉ",R.mipmap.ks1));
        arrMenu.add(new Menu("Quán bar",R.mipmap.bar1));
        arrMenu.add(new Menu("Trung tâm thương mại",R.mipmap.tttm1));
        arrMenu.add(new Menu("Siêu thị",R.mipmap.sieuthi1));
        arrMenu.add(new Menu("Trạm xăng",R.mipmap.cayxang1));

    }

    private  void nearPlaces(String place, double lat, double lng){
        List<Address> addressList = null;
        MarkerOptions userMarkerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(this);
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        String url = getUrl(lat, lng, place);
        transferData[0] = mMap;
        transferData[1] = url;
        getNearbyPlaces.execute(transferData);
        Toast.makeText(this, "Đang tìm " , Toast.LENGTH_SHORT).show();
        Log.d("1234",url);
    }

    private String getUrl(double latitide, double longitude, String nearbyPlace)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + 1000);
        googleURL.append("&types=" + nearbyPlace);
        googleURL.append("&key="+"AIzaSyA9Vf8Gc0CbgzzbjGltlxTuzNxz7PV26zw");

        return googleURL.toString();
    }
}
