package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
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

        Intent getData = getActivity().getIntent();
        DetailPlace data = (DetailPlace) getData.getParcelableExtra("DataPlaceTabHost");
        tab_txtAddress.setText(data.result.formatted_address);
        tab_txtPhone.setText(data.result.formatted_phone_number);
        tab_txtWebsite.setText(data.result.website);
        tab_txtRating.setText(data.result.rating + "");
        rat.setRating(data.result.rating);
        return v;
    }
}