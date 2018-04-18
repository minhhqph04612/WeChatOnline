package com.minh.wechatonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.minh.wechatonline.R;
import com.minh.wechatonline.model.ChatAllMessage;

import java.util.ArrayList;

/**
 * Created by HP on 4/16/2018.
 */

public class ChatAllAdapter extends ArrayAdapter<ChatAllMessage>{
    private Activity context ;
    private ArrayList<ChatAllMessage> messageList ;
    private int layoutId;


    public ChatAllAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ChatAllMessage> messageList) {
        super(context, resource, messageList);
        this.context = (Activity) context;
        this.layoutId = resource;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(layoutId,null);
        if(messageList.size()>0 && position >=0){
            final TextView txtUser=(TextView)
                    convertView.findViewById(R.id.message_user);
            final TextView txtTime=(TextView)
                    convertView.findViewById(R.id.message_time);
            final TextView txtText=(TextView)
                    convertView.findViewById(R.id.message_text_layout);
            final ChatAllMessage emp=messageList.get(position);

            txtUser.setText(emp.getMessageUser());
            txtTime.setText(emp.getMessageTime());
            txtText.setText(emp.getMessageText());
        }

       return convertView;
    }
}
