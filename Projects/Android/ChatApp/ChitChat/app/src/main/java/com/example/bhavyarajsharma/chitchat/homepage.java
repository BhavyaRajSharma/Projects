package com.example.bhavyarajsharma.chitchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class homepage extends AppCompatActivity {
    ArrayAdapterHomePage mMessageAdapter;
    ListView mMessageListView;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor ed;
    public static final String MyPREFERENCES = "MyPrefs";
    // Firebase instances
    FirebaseUser user;
    protected FirebaseStorage mFirebaseStorage;
    protected StorageReference mchatphoto;
    protected String mUsername;
    protected FirebaseDatabase mfireBaseDatabase;
    protected DatabaseReference mMessageDatabaseReference, mref;
    protected ChildEventListener mchildeventlistener;
    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseAuth.AuthStateListener mAuthstatelistener;
    protected List<friendly_list> friendlyMessages;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)

                Toast.makeText(this, "signed in", Toast.LENGTH_SHORT).show();
            else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
//            if(requestCode==RC_PHOTO_PICKER && requestCode==RESULT_OK){
//                Uri selectimageuri=data.getData();
//                StorageReference photoref=  mchatphoto.child(selectimageuri.getLastPathSegment());
//                photoref.putFile(selectimageuri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Uri download=taskSnapshot.getDownloadUrl();
//                        FriendlyMessage friendlyMessage=new FriendlyMessage(null,mUsername,download.toString(),my);
//
//                        mMessageDatabaseReference.push().setValue(friendlyMessage);
//                    }
//                });
//            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mMessageListView = (ListView) findViewById(R.id.activity_homepage_listview);
        friendlyMessages = new ArrayList<friendly_list>();
        mMessageAdapter = new ArrayAdapterHomePage(this, R.layout.homepage_item, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        mfireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mMessageDatabaseReference = mfireBaseDatabase.getReference().child("home");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        ed=sharedpreferences.edit();


        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Main3Activity.class);

                intent.putExtra("NUMBER",friendlyMessages.get(position).getNumber());
                startActivity(intent);

            }
        });


        // mchatphoto=mFirebaseStorage.getReference().child("number");
        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Toast.makeText(homepage.this, "user is logged in", Toast.LENGTH_SHORT).show();
                    String n = user.getPhoneNumber();




//                    mMessageDatabaseReference = mfireBaseDatabase.getReference().child("home");
//
//         //           DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("homepagelist");
//                    Query query = mMessageDatabaseReference.orderByChild("name").equalTo(user.getDisplayName());


//if(){
//   // Toast.makeText(homepage.this, mDatabaseReference.getRoot(), Toast.LENGTH_SHORT).show();
//    Toast.makeText(homepage.this,"hellllllllo", Toast.LENGTH_SHORT).show();
//}
//else{
//    Toast.makeText(homepage.this, "hiiii", Toast.LENGTH_SHORT).show();
                    String name = sharedpreferences.getString("user", "");
                    friendly_list friendlyMessage = new friendly_list(name, n);

                    mref = mfireBaseDatabase.getReference().child("home").child(user.getPhoneNumber());

                    mref.push().setValue(friendlyMessage);
                    onsignedin();


//}


                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                                    .build(),
                            1);
                    onsignedout();
                }

            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                ed.putString("login","");ed.commit();
                Intent in=new Intent(this,MainActivity.class);
                startActivity(in);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void onsignedin() {
        //  mUsername = name;
        mchildeventlistener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                friendly_list friend = dataSnapshot.getValue(friendly_list.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                   // Toast.makeText(homepage.this, ds.getValue().toString(), Toast.LENGTH_SHORT).show();
                    friend.setNumber(((HashMap) ds.getValue()).get("number").toString());
                    friend.setName(((HashMap) ds.getValue()).get("name").toString());
                    break;

                }


                //LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOK
                if(!friend.getNumber().equals(user.getPhoneNumber()))
                mMessageAdapter.add(friend);
                // mref=mfireBaseDatabase.getReference().child(user.getDisplayName());
                Log.d("hi", mref.getKey().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessageDatabaseReference.addChildEventListener(mchildeventlistener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthstatelistener);
        if (mchildeventlistener != null)
            mMessageDatabaseReference.removeEventListener(mchildeventlistener);
        mMessageAdapter.clear();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthstatelistener);
    }

    void onsignedout() {
        //  mUsername = "anonymus";
        mMessageAdapter.clear();
        if (mchildeventlistener != null)
            mMessageDatabaseReference.removeEventListener(mchildeventlistener);
    }

}
