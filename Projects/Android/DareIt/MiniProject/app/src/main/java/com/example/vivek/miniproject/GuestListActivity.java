package com.example.vivek.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GuestListActivity extends AppCompatActivity {

    private TextView textViewEventName;
    private ListView listViewGuestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        textViewEventName = (TextView) findViewById(R.id.tv_event_name_gl);
        listViewGuestName = (ListView) findViewById(R.id.lv_gl);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra(MainActivity.STRING_EVENT_NAME);
        ArrayList <String > guestList = intent.getStringArrayListExtra(MainActivity.STRING_NAME);

        textViewEventName.setText(eventName);

        GuestListAdapter guestListAdapter = new GuestListAdapter(GuestListActivity.this, guestList);
        listViewGuestName.setAdapter(guestListAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
