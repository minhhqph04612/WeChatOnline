package com.minh.wechatonline.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.minh.wechatonline.R;

/**
 * Created by HP on 4/2/2018.
 */

public class FriendHolder extends RecyclerView.ViewHolder {
    View view;

    public FriendHolder(View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setDate(String date){
        TextView tvStatus =(TextView) view.findViewById(R.id.user_single_status);
        tvStatus.setText(date);
    }
    public void setEmail(String email){
        TextView tvEmail =(TextView) view.findViewById(R.id.user_single_email);
        tvEmail.setText(email);
    }
}
