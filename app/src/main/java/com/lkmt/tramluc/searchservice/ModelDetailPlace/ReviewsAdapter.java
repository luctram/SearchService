package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.ModelMenu.Menu;
import com.lkmt.tramluc.searchservice.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<Reviews> menuList;

    public ReviewsAdapter(Context context, int layout, List<Reviews> menuList) {
        this.context = context;
        this.layout = layout;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        CircleImageView  imgReviewer = (CircleImageView) convertView.findViewById(R.id.imgReviewer);
        TextView tab_txtReviewName = (TextView) convertView.findViewById(R.id.tab_txtReviewName);
        TextView tab_txtTimeReview =(TextView) convertView.findViewById(R.id.tab_txtTimeReview);
        RatingBar rat = (RatingBar) convertView.findViewById(R.id.rat);
        TextView tab_txtContentReview = (TextView) convertView.findViewById(R.id.tab_txtContentReview);


        Reviews menuReviews = menuList.get(position);
        tab_txtContentReview.setText(menuReviews.getReviewTxt());
        tab_txtReviewName.setText(menuReviews.getAuthorName());
        tab_txtTimeReview.setText(menuReviews.getTime());
        rat.setRating(menuReviews.getReviewRating());
//        imgReviewer
        return convertView;
    }
}
