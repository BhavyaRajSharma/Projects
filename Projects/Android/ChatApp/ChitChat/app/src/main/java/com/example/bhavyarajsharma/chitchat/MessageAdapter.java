package com.example.bhavyarajsharma.chitchat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
   public String s1,s2;
    LinearLayout llay;
    RelativeLayout rlay;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        ImageView photoImageView1 = (ImageView) convertView.findViewById(R.id.photoImageView1);
        TextView messageTextView1 = (TextView) convertView.findViewById(R.id.messageTextView1);
        TextView authorTextView1 = (TextView) convertView.findViewById(R.id.nameTextView1);
        llay=(LinearLayout)convertView.findViewById(R.id.lay1);
        rlay=(RelativeLayout)convertView.findViewById(R.id.lay2);

        FriendlyMessage message = getItem(position);

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            messageTextView1.setVisibility(View.GONE);
            photoImageView1.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
            Glide.with(photoImageView1.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
            messageTextView1.setVisibility(View.VISIBLE);
            photoImageView1.setVisibility(View.GONE);
            messageTextView1.setText(message.getText());
        }
        FirebaseAuth fba=FirebaseAuth.getInstance();
        FirebaseUser you=fba.getCurrentUser();
        authorTextView.setText(message.getName());

        s1=message.getMyurl();
        s2=you.getDisplayName();


        if(s1!=null && s1.equals(s2)){
//            photoImageView.setPadding(1000,0,0,0);
////            photoImageView.setTextAlignment();
//            messageTextView.setPadding(1000,0,0,0);
//                    authorTextView.setPadding(1000,0,0,0);

            rlay.setVisibility(View.VISIBLE);
            llay.setVisibility(View.INVISIBLE);



        }
        else{
//            photoImageView.setPadding(0,0,0,0);
//            messageTextView.setPadding(0,0,0,0);

            llay.setVisibility(View.VISIBLE);
           rlay.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }
}
