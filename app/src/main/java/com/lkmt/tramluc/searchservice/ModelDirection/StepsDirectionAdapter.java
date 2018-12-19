package com.lkmt.tramluc.searchservice.ModelDirection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lkmt.tramluc.searchservice.ModelDetailPlace.Reviews;
import com.lkmt.tramluc.searchservice.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StepsDirectionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<StepsDirection> menuList;

    public StepsDirectionAdapter(Context context, int layout, List<StepsDirection> menuList) {
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

        TextView stepDescribe = (TextView) convertView.findViewById(R.id.stepDescribe);
        TextView stepDistance = (TextView) convertView.findViewById(R.id.stepDistance);
        TextView stepDuration = (TextView) convertView.findViewById(R.id.stepDuration);

        StepsDirection stepsDirection = menuList.get(position);
        stepDescribe.setText(stepsDirection.getDescribe());
        stepDistance.setText(stepsDirection.getDistanceStep().getText());
        stepDuration.setText(stepsDirection.getDurationStep().getText());

        return convertView;
    }
}
