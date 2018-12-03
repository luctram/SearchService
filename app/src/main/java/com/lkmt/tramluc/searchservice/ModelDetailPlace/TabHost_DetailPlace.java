package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.R;

public class TabHost_DetailPlace extends Fragment {
    TextView tab_txtRating =null, tab_txtKm=null, tab_txtHour =null,tab_txtAddress=null, tab_txtPhone=null,tab_txtWebsite=null;
    RatingBar rat;
    Button btnGoDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabhost_detail, container, false);
        //TextView e = (TextView) v.findViewById(R.id.textView);

        tab_txtRating = (TextView) v.findViewById(R.id.tab_txtRating);
        tab_txtKm=(TextView) v.findViewById(R.id.tab_txtKm);
        tab_txtHour =(TextView) v.findViewById(R.id.tab_txtHour);
        tab_txtAddress=(TextView) v.findViewById(R.id .tab_txtAddress);
        tab_txtPhone= (TextView) v.findViewById(R.id.tab_txtPhone);
        tab_txtWebsite=(TextView) v.findViewById(R.id.tab_txtWebsite);



        return v;
    }
}