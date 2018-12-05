package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    DetailPlace data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         //super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.tabhost_detail, container, false);
        tab_txtRating = (TextView) v.findViewById(R.id.tab_txtRating);
        tab_txtKm=(TextView) v.findViewById(R.id.tab_txtKm);
        tab_txtHour =(TextView) v.findViewById(R.id.tab_txtHour);
        tab_txtAddress=(TextView) v.findViewById(R.id.tab_txtAddress);
        tab_txtPhone= (TextView) v.findViewById(R.id.tab_txtPhone);
        tab_txtWebsite=(TextView) v.findViewById(R.id.tab_txtWebsite);
        rat = (RatingBar) v.findViewById(R.id.rat);
        if (data.result == null){
            return v;}
        tab_txtAddress.setText(data.result.formatted_address);
        tab_txtPhone.setText(data.result.formatted_phone_number);
        tab_txtWebsite.setText(data.result.website);
        tab_txtRating.setText(data.result.rating + "");
        rat.setRating(data.result.rating);
        if(data.result.reviews ==null){
            Log.d("CHECK123","Kco binh luan");
        }else {
            Log.d("CHECK123", data.result.reviews.get(0).getauthor_name()+"");
        }
        return v;
    }

    public void getData(DetailPlace data){
       this.data = data;
    }
}