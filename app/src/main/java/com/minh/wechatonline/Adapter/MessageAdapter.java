package com.minh.wechatonline.Adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.minh.wechatonline.R.layout.message_single_layout;

/**
 * Created by HP on 4/8/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> listMessage;

    public MessageAdapter(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(message_single_layout, parent, false);
        return new MessageViewHolder(view);
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public CircleImageView profileImage;
        public TextView messageTime;
        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            messageTime  =  (TextView) itemView.findViewById(R.id.message_text_time);
            profileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_layout);



        }
    }



    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String current_user_id =   FirebaseAuth.getInstance().getCurrentUser().getUid();

        Message c = listMessage.get(position);
        String from_user_id = c.getFrom();

        if(from_user_id.equals(current_user_id)){
            holder.messageText.setBackgroundResource(R.drawable.message_text_background);
            holder.messageText.setTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.messageText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            holder.profileImage.setImageResource(0);

        }else{

            holder.messageText.setBackgroundColor(Color.WHITE);
            holder.messageText.setTextColor(Color.BLACK);
            holder.profileImage.setImageResource(R.drawable.ic_person_black_24dp);
        }
        holder.messageText.setText(c.getMessage());


    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }





}
