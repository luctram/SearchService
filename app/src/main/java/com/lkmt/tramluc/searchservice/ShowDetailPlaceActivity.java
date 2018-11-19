package com.lkmt.tramluc.searchservice;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class ShowDetailPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailplace);

        TabHost tabhost;
        TabHost.TabSpec tab_detail, tab_reviews;
        Intent reviewIntent,detailIntent;

        tabhost = (TabHost) findViewById(android.R.id.tabs);

//        LocalActivityManager localActivityManager = new LocalActivityManager(this,false);
//        tabhost.setup(localActivityManager);
            tabhost.setup();

        //Tab show detail
        tab_detail = tabhost.newTabSpec("Tab one");
        tab_detail.setContent(R.id.Tab_Detail);
        tab_detail.setIndicator("Tổng quan");
        detailIntent = new Intent(this,TabHost_DetailPlace.class);
        tab_detail.setContent(detailIntent);


        //Tab show reviews
        tab_reviews = tabhost.newTabSpec("Tab two");
        tab_reviews.setContent(R.id.Tab_Reviews);
        tab_reviews.setIndicator("Xem bình luận");
        reviewIntent = new Intent(this,TabHost_Reviews.class);
        tab_reviews.setContent(reviewIntent);

        tabhost.addTab(tab_detail);
        tabhost.addTab(tab_reviews);


    }
}
