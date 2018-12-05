package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.ModelMenu.Menu;
import com.lkmt.tramluc.searchservice.ModelMenu.MenuAdapter;
import com.lkmt.tramluc.searchservice.R;

import java.util.ArrayList;

public class TabHost_Reviews extends Fragment {
    ListView listViewReviews;
    ArrayList<Reviews> arrReviews;
    ReviewsAdapter adapter;
    DetailPlace data;
    TextView txtNullReview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabhost_reviews, container, false);
        //TextView e = (TextView) v.findViewById(R.id.textView);
        adapter = new ReviewsAdapter(inflater.getContext(),R.layout.row_listview_reviews,arrReviews);
        listViewReviews.setAdapter(adapter);
        txtNullReview = (TextView) v.findViewById(R.id.txtNullReview);
        if (data.result == null){
            txtNullReview.setVisibility(View.VISIBLE);
            return v;}


        if (data.result.reviews == null){
            txtNullReview.setVisibility(View.VISIBLE);
        }
        else{
            anhxa(v);
        }
        return v;
    }

    private void anhxa(View v){
        listViewReviews = (ListView) v.findViewById(R.id.listview_reviews);
        arrReviews = new ArrayList<Reviews>();
        for (int i=0; i< data.result.reviews.size(); i++){
            arrReviews.add(new Reviews(data.result.reviews.get(i).author_name,data.result.reviews.get(i).text,data.result.reviews.get(i).relative_time_description,data.result.reviews.get(i).rating,data.result.reviews.get(i).profile_photo_url));
        }
    }
    public void getData(DetailPlace data){
        this.data = data;
    }
}
