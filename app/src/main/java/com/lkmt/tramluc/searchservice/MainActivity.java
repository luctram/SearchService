package com.lkmt.tramluc.searchservice;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.ResultDetailPlace;
import com.lkmt.tramluc.searchservice.ModelMenu.Menu;
import com.lkmt.tramluc.searchservice.ModelMenu.MenuAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lkmt.tramluc.searchservice.Realm.ServicesDB;
import com.lkmt.tramluc.searchservice.Realm.TypeServiceDB;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private GoogleMap mMap;
    DatabaseReference mData;
    ListView listViewmenu;
    ArrayList<Menu> arrMenu;
    MenuAdapter adapter;
    Button btnUpdate;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        String[] listCities = new String[]{"Thành phố Hồ Chí Minh","Hà Nội","Nha Trang","Vũng Tàu","Phan Thiết","Đà Lạt","Cần Thơ", "Đà Nẵng","Sa Pa"};

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

            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Quá trình cập nhập dữ liệu sẽ tốn vài phút. Bạn có muốn cập nhập không?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Có",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ServicesDB service = new ServicesDB();
                            service.addToRealm();
                        }
                    });

            builder1.setNegativeButton(
                    "Không",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            }
        });


        anhxa();
        adapter = new MenuAdapter(this,R.layout.row_listview_menu,arrMenu);
        listViewmenu.setAdapter(adapter);

        listViewmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
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

}
