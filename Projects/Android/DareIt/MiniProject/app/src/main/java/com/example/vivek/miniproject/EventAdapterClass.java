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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vivek on 12/4/2017.
 */

public class EventAdapterClass extends ArrayAdapter<Event_Details>
{
    private Activity context;
    private List <Event_Details> eventList;
    String disp = "No of Guests Visiting : ";
    private DatabaseReference databaseReferenceUsers;


    public EventAdapterClass (Activity context, List <Event_Details> eventList)
    {
        super(context,R.layout.event_list_layout, eventList);
        this.context = context;
        this.eventList = eventList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.event_list_layout, null, true);

        TextView textViewEventName = (TextView) listViewItem.findViewById(R.id.ell_event_name);
        TextView textViewEventDetails = (TextView) listViewItem.findViewById(R.id.ell_event_details);
        TextView textViewEventVenue = (TextView) listViewItem.findViewById(R.id.ell_event_venue);
        TextView textViewEventTime = (TextView) listViewItem.findViewById(R.id.ell_event_date);
        TextView textViewEventGuest = (TextView) listViewItem.findViewById(R.id.ell_event_guests);
        TextView textViewEventPerStatus = (TextView) listViewItem.findViewById(R.id.ell_event_per_status);

        Event_Details event_details = eventList.get(position);

        textViewEventName.setText(event_details.getEvent_name());
        textViewEventDetails.setText(event_details.getEvent_details());
        textViewEventVenue.setText(event_details.getEvent_venue());
        textViewEventGuest.setText(disp+String.valueOf(event_details.getNoOfGuests()));
        textViewEventTime.setText(new Date(event_details.getDateOfEvent()).toString());

        List <String> list =(event_details.getGuestList());
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        if(list.contains(firebaseUser.getUid()))
            textViewEventPerStatus.setText(MainActivity.GOING);
        else if(firebaseUser.getUid().equals(event_details.getIdofCreator()))
            textViewEventPerStatus.setText("Host");
        else
            textViewEventPerStatus.setText(MainActivity.NOT_GOING);
        return  listViewItem;
    }
}
