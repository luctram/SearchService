package com.lkmt.tramluc.searchservice.ModelDetailPlace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        tab_txtContentReview.setText(menuReviews.getText());
        tab_txtReviewName.setText(menuReviews.getAuthor_name());
        tab_txtTimeReview.setText(menuReviews.getRelative_time_description());
        rat.setRating(menuReviews.getRating());

        URL url = null;
        try {
            url = new URL(menuReviews.profile_photo_url);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imgReviewer.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
