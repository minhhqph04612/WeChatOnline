package com.minh.wechatonline.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.minh.wechatonline.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 4/1/2018.
 */

public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View view;
    public SearchHolder(View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setEmail(String email){
        TextView tvEmail = (TextView) view.findViewById(R.id.user_single_email);
        tvEmail.setText(email);
    }
    public void setStatus(String status){
        TextView tvStatus = (TextView) view.findViewById(R.id.user_single_status);
        tvStatus.setText(status);
    }
    public  void setImage(String image, Context context){
        CircleImageView userImage = (CircleImageView) view.findViewById(R.id.user_single_image);
        Picasso.with(context).load(image).placeholder(R.mipmap.ic_account_circle_white_24dp).into(userImage);
    }
    @Override
    public void onClick(View view) {

    }

}
