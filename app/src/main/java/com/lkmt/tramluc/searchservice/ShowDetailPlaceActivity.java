package com.lkmt.tramluc.searchservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class ShowDetailPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailplace);

        TabHost tabhost = (TabHost) findViewById(R.id.tabs);
        tabhost.setup();

        //Tab show detail
        TabHost.TabSpec tab_detail = tabhost.newTabSpec("Tab one");
        tab_detail.setContent(R.id.Tab_Detail);
        tab_detail.setIndicator("Tổng quan");
        tabhost.addTab(tab_detail);

        //Tab show reviews
        TabHost.TabSpec tab_reviews = tabhost.newTabSpec("Tab two");
        tab_reviews.setContent(R.id.Tab_Reviews);
        tab_reviews.setIndicator("Xem bình luận");
        tabhost.addTab(tab_reviews);


    }
}
