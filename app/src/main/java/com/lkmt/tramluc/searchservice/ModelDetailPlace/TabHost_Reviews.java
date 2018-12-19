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
<<<<<<< HEAD
=======

    private void anhxa(View v){
        listViewReviews = (ListView) v.findViewById(R.id.listview_reviews);
        arrReviews = new ArrayList<Reviews>();
        for (int i=0; i< data.result.reviews.size(); i++){

            arrReviews.add(new Reviews(data.result.reviews.get(i).author_name,data.result.reviews.get(i).relative_time_description,data.result.reviews.get(i).profile_photo_url,data.result.reviews.get(i).rating,data.result.reviews.get(i).text));

        }

   }
    public void getData(DetailPlace data){
        this.data = data;
    }
>>>>>>> 9632b8ba35aa1067c79a2bdfb081dbf8a873e929
}
