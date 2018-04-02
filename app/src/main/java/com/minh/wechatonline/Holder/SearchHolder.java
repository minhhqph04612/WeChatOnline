package com.minh.wechatonline.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.minh.wechatonline.R;

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

    @Override
    public void onClick(View view) {

    }

}
