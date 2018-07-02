package com.example.vivek.miniproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Vivek on 12/5/2017.
 */

public class GuestListAdapter extends ArrayAdapter <String>
{
    private Activity context;
    private List <String> guestList;

   public GuestListAdapter(Activity context, List <String> guestList)
   {
       super(context,R.layout.guest_layout,guestList);
       this.context = context;
       this.guestList = guestList;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.guest_layout, null, true);

        TextView textViewGuestName = listViewItem.findViewById(R.id.guest_name);

        String name = guestList.get(position);

        textViewGuestName.setText(name);

        return listViewItem;
    }
}
