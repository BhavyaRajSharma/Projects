package com.example.bhavyarajsharma.chitchat;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ArrayAdapterHomePage extends ArrayAdapter<friendly_list> {
    public String s1,s2;
    public ArrayAdapterHomePage(Context context, int resource, List<friendly_list> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_list_homepage, parent, false);
        }

      //  ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.item_list_homepage_textview1);
        TextView numberTextView = (TextView) convertView.findViewById(R.id.item_list_homepage_textview2);
        //TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        friendly_list message = getItem(position);

//        boolean isPhoto = message.getPhotoUrl() != null;
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUrl())
//                    .into(photoImageView);
//        } else {
//            messageTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
        messageTextView.setText(message.getName());
        numberTextView.setText( message.getNumber());
//        }
//        FirebaseAuth fba=FirebaseAuth.getInstance();
//        FirebaseUser you=fba.getCurrentUser();
//        authorTextView.setText(message.getName());
//
//        s1=message.getMyurl();
//        s2=you.getDisplayName();
//
//
//        if(s1!=null && s1.equals(s2)){
//            photoImageView.setPadding(1000,0,0,0);
////            photoImageView.setTextAlignment();
//            messageTextView.setPadding(1000,0,0,0);
//            authorTextView.setPadding(1000,0,0,0);
////           messageTextView.setText(s2);
////            authorTextView.setText(s1);
//        }
//        else{
//            photoImageView.setPadding(0,0,0,0);
//            messageTextView.setPadding(0,0,0,0);
//            authorTextView.setPadding(0,0,0,0);
////            messageTextView.setText("abcdef");
//        }

        return convertView;
    }
}
