package com.example.bhavyarajsharma.chitchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.edit;

public class Main3Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int RC_PHOTO_PICKER =  2;
    public static String my="";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor ed;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    FirebaseUser user1;
    private FirebaseStorage mFirebaseStorage1;
    private StorageReference mchatphoto1;
    private String mUsername1;
    private FirebaseDatabase mfireBaseDatabase1;
    private DatabaseReference mMessageDatabaseReference1;
    private ChildEventListener mchildeventlistener1;
    private FirebaseAuth mFirebaseAuth1;
    private FirebaseAuth.AuthStateListener mAuthstatelistener1;
    private FirebaseRemoteConfig mfirebaseremote1;
    Bundle b;
    String number1;
    homepage hp=new homepage();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK)

                Toast.makeText(this, "signed in", Toast.LENGTH_SHORT).show();
            else if(requestCode==RESULT_CANCELED){
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
            if(requestCode==RC_PHOTO_PICKER && requestCode==RESULT_OK){
                Uri selectimageuri=data.getData();
                StorageReference photoref=  mchatphoto1.child(selectimageuri.getLastPathSegment());
                photoref.putFile(selectimageuri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri download=taskSnapshot.getDownloadUrl();
                        FriendlyMessage friendlyMessage=new FriendlyMessage(null,mUsername1,download.toString(),my);

                        mMessageDatabaseReference1.push().setValue(friendlyMessage);
                    }
                });
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mfireBaseDatabase1 = FirebaseDatabase.getInstance();
        mFirebaseAuth1 = FirebaseAuth.getInstance();
        mFirebaseStorage1=FirebaseStorage.getInstance();


        mfirebaseremote1=FirebaseRemoteConfig.getInstance();
        mUsername1 = ANONYMOUS;
        b=getIntent().getExtras();
        user1=mFirebaseAuth1.getCurrentUser();
        String number=b.getString("NUMBER");
        String number2=user1.getPhoneNumber();
        if(number.compareTo(number2)<0){
            number1=number+number2;

        }
        else number1=number2+number;
        mMessageDatabaseReference1 = mfireBaseDatabase1.getReference().child(number1);
        mchatphoto1=mFirebaseStorage1.getReference().child(number1);

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        sharedpreferences =getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        ed=sharedpreferences.edit();
        mUsername1=sharedpreferences.getString("user","");
        onsignedin(mUsername1);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername1, null,my);

                mMessageDatabaseReference1.push().setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");

            }
        });


//        mAuthstatelistener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                user = firebaseAuth.getCurrentUser();
//
//                if (user != null) {
//                    Toast.makeText(Main3Activity.this, "user is logged in", Toast.LENGTH_SHORT).show();
//                    onsignedin(my=user.getDisplayName());
//
//                } else {
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(
//                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
//                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
//                                    .build(),
//                            1);
//                    onsignedout();
//                }
//
//            }
//        };
        FirebaseRemoteConfigSettings setting=new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(com.firebase.ui.auth.BuildConfig.DEBUG).build();
        mfirebaseremote1.setConfigSettings(setting);
        Map<String,Object> defaultconfigg=new HashMap<>();
        defaultconfigg.put("key",DEFAULT_MSG_LENGTH_LIMIT);
        mfirebaseremote1.setDefaults(defaultconfigg);
        fetch();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.sign_out_menu:AuthUI.getInstance().signOut(this);ed.putString("login","");ed.commit();
                Intent in=new Intent(this,MainActivity.class);
                startActivity(in);
                ;return true;
            default:
                return super.onOptionsItemSelected(item);}
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        mFirebaseAuth1.removeAuthStateListener(hp.mAuthstatelistener);
//        mMessageDatabaseReference1.removeEventListener(mchildeventlistener1);
//        mMessageAdapter.clear();
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth1.addAuthStateListener(hp.mAuthstatelistener);
//    }


    void onsignedin(String name) {
        mUsername1 = name;
        mchildeventlistener1 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FriendlyMessage friend = dataSnapshot.getValue(FriendlyMessage.class);
                mMessageAdapter.add(friend);

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
        mMessageDatabaseReference1.addChildEventListener(mchildeventlistener1);
    }

    void onsignedout() {
        mUsername1 = "anonymus";
        mMessageAdapter.clear();
        mMessageDatabaseReference1.removeEventListener(mchildeventlistener1);
    }
    void fetch(){
        long cache=3000;
        if(mfirebaseremote1.getInfo().getConfigSettings().isDeveloperModeEnabled()){
            cache=0;
        }
        mfirebaseremote1.fetch(cache).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mfirebaseremote1.activateFetched();
                apply();
            }
        });
    }
    void apply(){
        long flength=mfirebaseremote1.getLong("key");
        mMessageEditText.setFilters(new InputFilter[]{new  InputFilter.LengthFilter(((int) flength))});}
}


