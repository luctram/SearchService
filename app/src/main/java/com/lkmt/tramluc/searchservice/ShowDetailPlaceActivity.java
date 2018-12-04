package com.lkmt.tramluc.searchservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lkmt.tramluc.searchservice.ModelDetailPlace.DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_DetailPlace;
import com.lkmt.tramluc.searchservice.ModelDetailPlace.TabHost_Reviews;

public class ShowDetailPlaceActivity extends AppCompatActivity {

    FragmentTabHost tabhost;
    FragmentTabHost.TabSpec tab_detail, tab_reviews;
    Intent reviewIntent,detailIntent;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailplace);

        tabhost = (FragmentTabHost) findViewById(R.id.tabsHost);
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        //Tab show detail
        tab_detail = tabhost.newTabSpec("Tab one").setIndicator("Chi tiết");
        tab_detail.setIndicator("Chi tiết");

        tabhost.getTabWidget().getChildAt(0).


        //Tab show reviews
        tab_reviews = tabhost.newTabSpec("Tab two");
        tab_reviews.setIndicator("Xem bình luận");
        tabhost.addTab(tab_detail, TabHost_DetailPlace.class,null);
        tabhost.addTab(tab_reviews, TabHost_Reviews.class,null);
        tabhost.setCurrentTab(0);

        Intent getData = getIntent();
        DetailPlace data = (DetailPlace) getData.getParcelableExtra("DataPlace");
//
        Intent intentDetail = new Intent(ShowDetailPlaceActivity.this, TabHost_DetailPlace.class);
        intentDetail.putExtra("DataPlaceTabHost",getData.getParcelableExtra("DataPlace"));
        tab_detail.setContent(intentDetail);
////        startActivity(intentDetail);
//
//        Intent intentReviews = new Intent(ShowDetailPlaceActivity.this, TabHost_DetailPlace.class);
//        intentReviews.putExtra("DataPlaceTabHost",getData.getParcelableExtra("DataPlace"));
////        startActivity(intentReviews);
    }
}
