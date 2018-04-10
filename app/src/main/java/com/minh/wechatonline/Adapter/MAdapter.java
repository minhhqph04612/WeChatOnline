package com.minh.wechatonline.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 4/9/2018.
 */

public class MAdapter extends ArrayAdapter<Message> {
    public MAdapter(@NonNull Context context, int resource, ArrayList<Message> messages) {
        super(context, 0,messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message =  getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_single_layout,parent,false);

        }
        TextView tvMessage =  (TextView) convertView.findViewById(R.id.message_text_layout);
        CircleImageView profileImage = (CircleImageView) convertView.findViewById(R.id.message_profile_layout);
        tvMessage.setText(message.getMessage());
        return convertView;
    }
}
