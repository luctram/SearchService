package com.lkmt.tramluc.searchservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;

import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_Reviews;

public class ShowDetailPlaceActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailplace);

        FragmentTabHost tabhost;
        FragmentTabHost.TabSpec tab_detail, tab_reviews;
        Intent reviewIntent,detailIntent;
//
        tabhost = (FragmentTabHost) findViewById(R.id.tabsHost);
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
//
//        LocalActivityManager localActivityManager = new LocalActivityManager(this,false);
//        tabhost.setup(localActivityManager);
//        tabhost.setup();
//        tabhost.setup();

        //Tab show detail
        tab_detail = tabhost.newTabSpec("Tab one").setIndicator("Chi tiết");
        tab_detail.setIndicator("Chi tiết");


        //Tab show reviews
        tab_reviews = tabhost.newTabSpec("Tab two");
        tab_reviews.setIndicator("Xem bình luận");
        tabhost.addTab(tab_detail, TabHost_DetailPlace.class,null);
        tabhost.addTab(tab_reviews, TabHost_Reviews.class,null);
//        tabhost.addTab(tabhost.newTabSpec("simple").setIndicator("Simple"),
//                TabHost_DetailPlace.class, null);
        tabhost.setCurrentTab(0);

    }
}
