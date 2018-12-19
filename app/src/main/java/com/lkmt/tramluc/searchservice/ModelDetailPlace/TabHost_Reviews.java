package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lkmt.tramluc.searchservice.R;

public class TabHost_Reviews extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabhost_reviews, container, false);
        //TextView e = (TextView) v.findViewById(R.id.textView);
        return v;
    }
}
