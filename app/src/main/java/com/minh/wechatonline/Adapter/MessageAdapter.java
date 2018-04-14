package com.minh.wechatonline.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minh.wechatonline.R;
import com.minh.wechatonline.model.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.minh.wechatonline.R.layout.message_single_layout;

/**
 * Created by HP on 4/8/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> listMessage;
    private DatabaseReference userDatabase;
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
        public TextView userEmail;
        public ImageView messageImage;
        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            messageTime  =  (TextView) itemView.findViewById(R.id.message_text_time);
            profileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_layout);
            //userEmail = (TextView) itemView.findViewById(R.id.message_single_user_email);
            messageImage = (ImageView) itemView.findViewById(R.id.message_single_image);

        }
    }



    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        //final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        final String current_user_id =   FirebaseAuth.getInstance().getCurrentUser().getUid();

        Message c = listMessage.get(position);
        final String from_user_id = c.getFrom();
        String message_type = c.getType();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(from_user_id);
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String userEmail = dataSnapshot.child("email").getValue().toString();
                String userImage = dataSnapshot.child("image").getValue().toString();
                //holder.userEmail.setText(userEmail);
                if(from_user_id.equals(current_user_id)){
                    holder.messageText.setBackgroundResource(R.drawable.message_text_background);
                    holder.messageText.setTextColor(Color.WHITE);
                    Picasso.with(holder.profileImage.getContext()).load(userImage).placeholder(R.drawable.ic_person_black_24dp).into(holder.profileImage);

                }else{

                    holder.messageText.setBackgroundColor(Color.WHITE);
                    holder.messageText.setTextColor(Color.BLACK);
                    Picasso.with(holder.profileImage.getContext()).load(userImage).placeholder(R.drawable.ic_person_black_24dp).into(holder.profileImage);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        if(message_type.equals("text")){
//            holder.messageText.setText(c.getMessage());
//            holder.profileImage.setVisibility(View.INVISIBLE);
//        }else {
//            holder.messageText.setVisibility(View.INVISIBLE);
//            holder.messageImage.setMaxWidth(100);
//            holder.messageImage.setMaxHeight(100);
//            Picasso.with(holder.messageImage.getContext()).load(c.getMessage()).placeholder(R.drawable.ic_person_black_24dp).into(holder.messageImage);
//
//        }


        holder.messageText.setText(c.getMessage());


    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }





}
