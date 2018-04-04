package com.minh.wechatonline.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.minh.wechatonline.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 4/2/2018.
 */

public  class FriendHolder extends RecyclerView.ViewHolder {
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
    public  void setImage(String image, Context context){
        CircleImageView userImage = (CircleImageView) view.findViewById(R.id.user_single_image);
        Picasso.with(context).load(image).placeholder(R.drawable.ic_person_black_24dp).into(userImage);
    }
}
