package com.example.vivek.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.vivek.miniproject.R.layout.content_main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String NODE_EVENT = "Events";
    public static final String NODE_USER = "Users";
    public static final String NOT_GOING = "Not Going";
    public static final String GOING = "Going";
    public static final String STRING_NAME = "MeriString";
    public static final String STRING_EVENT_NAME = "MeriStringNaam";


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView textView_email;
    private TextView textView_Uid;
    private TextView textView_name;


    private ListView listViewEventEvent;
    private DatabaseReference databaseReferenceEvents;
    private DatabaseReference databaseReferenceUsers;
    private List <Event_Details> listEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,CreateEventActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }


        textView_email = (TextView) headerView.findViewById(R.id.nav_email);
        textView_name = (TextView) headerView.findViewById(R.id.nav_name);
        textView_Uid = (TextView) headerView.findViewById(R.id.nav_hdr_id);

        textView_email.setText(firebaseUser.getEmail());
        textView_name.setText(firebaseUser.getDisplayName());
        textView_Uid.setText(firebaseUser.getUid());

        listEvent = new ArrayList<>();
        databaseReferenceEvents = FirebaseDatabase.getInstance().getReference(MainActivity.NODE_EVENT);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference(MainActivity.NODE_USER);
        listViewEventEvent = (ListView) findViewById(R.id.EventEvent);

        listViewEventEvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Event_Details event_details = listEvent.get(i);
                //Toast.makeText(MainActivity.this, "Item has been clicked "+event_details.getEvent_name(),Toast.LENGTH_LONG).show();

                if(firebaseUser.getUid().equals(event_details.getIdofCreator()))
                {
                    Toast.makeText(MainActivity.this, "You are the host !!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GuestListActivity.class);
                    intent.putStringArrayListExtra(MainActivity.STRING_NAME, (ArrayList<String>) event_details.getGuestList());
                    intent.putExtra(MainActivity.STRING_EVENT_NAME, event_details.getEvent_name());
                    startActivity(intent);


                }
                else
                {
                    List<String> list = event_details.getGuestList();
                        //list.addAll(event_details.getGuestList());
                    if(list.contains(firebaseUser.getUid()))
                        Toast.makeText(MainActivity.this, "Already voted for the event!! Have Fun !!", Toast.LENGTH_SHORT).show();
                    else {
                        event_details.setNoOfGuests(event_details.getNoOfGuests() + 1);
                        list.add(firebaseUser.getUid());
                        event_details.setGuestList(list);
                        databaseReferenceEvents.child(event_details.getEvent_id()).setValue(event_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(MainActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(MainActivity.this, "Error Updating!! Check your net Connection!!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        onSSStart();
                    }

                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        onSSStart();
    }

    protected void onSSStart()
    {

        databaseReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                listEvent.clear();
                for(DataSnapshot eventSnapshot :dataSnapshot.getChildren())
                {
                    Event_Details event_details = eventSnapshot.getValue(Event_Details.class);
                    listEvent.add(event_details);
                }

                EventAdapterClass eventAdapterClass = new EventAdapterClass(MainActivity.this, listEvent);
                listViewEventEvent.setAdapter(eventAdapterClass);


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(MainActivity.this, "Could not fetch the data !!! try again!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void OnMyEvent()
    {
        final String currentUID = firebaseUser.getUid();
        databaseReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                listEvent.clear();
                for(DataSnapshot eventSnapshot :dataSnapshot.getChildren())
                {
                    Event_Details event_details = eventSnapshot.getValue(Event_Details.class);
                    if(currentUID.equals(event_details.getIdofCreator()))
                        listEvent.add(event_details);
                }

                EventAdapterClass eventAdapterClass = new EventAdapterClass(MainActivity.this, listEvent);
                listViewEventEvent.setAdapter(eventAdapterClass);


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(MainActivity.this, "Could not fetch the data !!! try again!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_events)
        {

            OnMyEvent();
        }
        else if(id==R.id.all_events)
            onSSStart();

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account)
        {
            Intent intent = new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about_us)
        {
            Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
