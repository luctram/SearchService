package com.lkmt.tramluc.searchservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.ModelDetailPlace.DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_Reviews;

public class ShowDetailPlaceActivity extends AppCompatActivity {

    FragmentTabHost tabhost;
    DetailPlace data;
    Intent reviewIntent,detailIntent;
    TextView txtplaceName;
    String dataKm, dataHour;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailplace);
        tabhost = (FragmentTabHost) findViewById(R.id.tabsHost);
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //Tab show detail

        tabhost.addTab(tabhost.newTabSpec("Tab one").setIndicator("Chi tiết"), TabHost_DetailPlace.class,null);
        tabhost.addTab(tabhost.newTabSpec("Tab two").setIndicator("Xem bình luận"), TabHost_Reviews.class,null);
        tabhost.setCurrentTab(0);
//
        Intent getData = getIntent();
        data = (DetailPlace) getData.getParcelableExtra("DataPlace");
        dataHour = getData.getStringExtra("dataHour");
        dataKm = getData.getStringExtra("dataKm");

        txtplaceName = findViewById(R.id.txtplaceName);
        txtplaceName.setText(data.result.name);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment.getClass() == TabHost_DetailPlace.class) {
            TabHost_DetailPlace detailPlace = (TabHost_DetailPlace) fragment;
            detailPlace.getData(data);

            TabHost_DetailPlace dataKm = (TabHost_DetailPlace) fragment;
//            dataKm.getDataKm(dataKm);
//


        }

        if(fragment.getClass() == TabHost_Reviews.class){
            TabHost_Reviews reviews = (TabHost_Reviews) fragment;
            reviews.getData(data);
        }
    }
}
