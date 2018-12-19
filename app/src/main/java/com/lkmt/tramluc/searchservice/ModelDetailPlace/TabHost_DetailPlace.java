package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.R;

public class TabHost_DetailPlace extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabhost_detail, container, false);
<<<<<<< HEAD
        //TextView e = (TextView) v.findViewById(R.id.textView);
=======
        tab_txtRating = (TextView) v.findViewById(R.id.tab_txtRating);
        tab_txtKm=(TextView) v.findViewById(R.id.tab_txtKm);
        tab_txtHour =(TextView) v.findViewById(R.id.tab_txtHour);
        tab_txtAddress=(TextView) v.findViewById(R.id.tab_txtAddress);
        tab_txtPhone= (TextView) v.findViewById(R.id.tab_txtPhone);
        tab_txtWebsite=(TextView) v.findViewById(R.id.tab_txtWebsite);
        rat = (RatingBar) v.findViewById(R.id.rat);
        if (data.result == null){
            return v;}
        tab_txtAddress.setText(new String(data.result.formatted_address));
        tab_txtPhone.setText(new String(data.result.formatted_phone_number));
        tab_txtWebsite.setText(data.result.website);
        tab_txtRating.setText(data.result.rating + "");
        rat.setRating(data.result.rating);
//        tab_txtKm.setText(dataKm);
//        tab_txtHour.setText(dataHour);
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
        return v;
    }
}