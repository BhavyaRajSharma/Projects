package com.example.vivek.miniproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity
{
    TextView textViewEventName;
    TextView textViewEventDetails;
    TextView textViewEventVenue;
    ProgressBar progressBar;
    DatePicker datePicker;
    TimePicker timePicker;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.NODE_EVENT);

        textViewEventName = (TextView) findViewById(R.id.ce_event_name);
        textViewEventDetails = (TextView) findViewById(R.id.ce_event_details);
        textViewEventVenue = (TextView) findViewById(R.id.ce_event_place);
        progressBar = (ProgressBar) findViewById(R.id.ce_event_progressBar);
        timePicker = (TimePicker) findViewById(R.id.ce_event_timePicker);
        datePicker = (DatePicker) findViewById(R.id.ce_event_datePicker);


    }

    public void inputIntoDatabase(View view)
    {
        String name = textViewEventName.getText().toString().trim();
        String details = textViewEventDetails.getText().toString().trim();
        String venue = textViewEventVenue.getText().toString().trim();
        String id = databaseReference.push().getKey();
        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        java.util.Date currentDate = new java.util.Date();
        long currentDateLong = currentDate.getTime();
        java.util.Date dateOfEvent = new java.util.Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0);
        long dateOfEventLong = dateOfEvent.getTime();
        List <String> guestList = new ArrayList<String>();


        if(TextUtils.isEmpty(name))
        {
            textViewEventName.setError("Event Name can't be blank");
            return;
        }
        if(TextUtils.isEmpty(details))
        {
            textViewEventDetails.setError("Event Details can't be blank");
            return;
        }
        if(TextUtils.isEmpty(venue))
        {
            textViewEventVenue.setError("Event Venue can't be blank");
            return ;

        }
        guestList.add("-- List of Guests Attending --");

        Event_Details event_details1 = new Event_Details(id,name,details,venue,dateOfEventLong,currentDateLong,creatorID,0,guestList,MainActivity.NOT_GOING);

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.child(id).setValue(event_details1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Toast.makeText(CreateEventActivity.this, "Event Successfully Added", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(CreateEventActivity.this, "Could not create the event...check your internet connection !!", Toast.LENGTH_LONG).show();
                }


            }
        });




    }
}
