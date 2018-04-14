package com.minh.wechatonline.News;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minh.wechatonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 3/22/2018.
 */

public class NewsAdapter   extends ArrayAdapter<NewObject> {
    private LayoutInflater layoutInflater;

    public NewsAdapter(@NonNull Context context,
                       int resource,
                       ArrayList<NewObject> objects, LayoutInflater layoutInflater) {
        super(context, resource,objects);
        this.layoutInflater= layoutInflater;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.news_item, null);
        }
        ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.imgAvatar);
        TextView title = (TextView) convertView.findViewById(R.id.tvTitle);


        NewObject  newObject  =  getItem(position);
        title.setText(newObject.getTitle());


        //View Anh
        Picasso.with(layoutInflater.getContext()).load(newObject.getImageURL()).into(imgAvatar);
        return convertView;
    }

}
