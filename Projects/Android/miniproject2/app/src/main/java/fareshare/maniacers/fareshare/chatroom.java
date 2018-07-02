package fareshare.maniacers.fareshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class chatroom extends AppCompatActivity {
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        if(flag==0) {
            ImageView imageView_1 = (ImageView) findViewById(R.id.chatroom_zoomin);
            if (imageView_1.getVisibility() == View.GONE) {
                super.onBackPressed();
                finish();
            }

            RelativeLayout relativeLayout_1 = (RelativeLayout) findViewById(R.id.chatroom_zoomout);
            imageView_1.setVisibility(View.GONE);
            relativeLayout_1.setVisibility(View.VISIBLE);
        }
        else
        {
            flag=0;
            RelativeLayout rr1;
            rr1=(RelativeLayout)findViewById(R.id.user_sharer_info);
            rr1.setVisibility(View.GONE);
            rr1=(RelativeLayout)findViewById(R.id.user_chatbox);
            rr1.setVisibility(View.VISIBLE);
        }

    }
    private FirebaseAuth mAuth;
    //Bundle bundle;
    SendDetails sendDetails;
    ImageButton imageSend;
    public static String f_flag;

    private ChatRoomAdapter mMessageAdapter;

    private static final int RC_PHOTO_PICKER = 2;
    FirebaseDatabase database;
    private ListView mMessageListView;
    DatabaseReference myRef;
    EditText titleSend,contentSend;
    Button send;
    FirebaseUser user1;
    private FirebaseStorage mFirebaseStorage1;
    private StorageReference mchatphoto1;
    private String mUsername1;
    private FirebaseDatabase mfireBaseDatabase1;
    private DatabaseReference mMessageDatabaseReference1,mMessageDatabaseReference2;
    private ChildEventListener mchildeventlistener1;
    private FirebaseAuth mFirebaseAuth1;
    private FirebaseAuth.AuthStateListener mAuthstatelistener1;
    TextView daily_answer;
    public static String s_s_s;
    Bundle bundle;

    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    SendDetails friendlyMessage = new SendDetails();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK)
//
//                Toast.makeText(this, "signed in", Toast.LENGTH_SHORT).show();
//            else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//        }

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectimageuri = data.getData();
            // my = user1.getPhoneNumber();
            StorageReference photoref = mchatphoto1.child(selectimageuri.getLastPathSegment());
            photoref.putFile(selectimageuri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri download = taskSnapshot.getDownloadUrl();
                    friendlyMessage.setChallange_image(download.toString());
                    friendlyMessage.setChallenge_sender_id(firebaseUser.getUid());
                    friendlyMessage.setChallenge_sender_name(firebaseUser.getDisplayName());
                    friendlyMessage.setSender_email(firebaseUser.getEmail());
                    friendlyMessage.setChallenge_v("1");
                    friendlyMessage.setChallenge_receiver_id(bundle.getString("uid"));
                    friendlyMessage.setChallenge_receiver_name(bundle.getString("name"));
                    friendlyMessage.setReceiver_email(bundle.getString("email"));
                    friendlyMessage.setChallenge_content("");
                    mMessageDatabaseReference1.push().setValue(friendlyMessage);
                    //mMessageDatabaseReference1.push().setValue(friendlyMessage);
                }
            });
        }
    }

public static String doj_n,s_s;
    public static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        bundle=getIntent().getExtras();
        sendDetails=new SendDetails();
        sendDetails.setChallenge_receiver_id(bundle.getString("uid"));
        sendDetails.setChallenge_receiver_name(bundle.getString("name"));
        sendDetails.setReceiver_email(bundle.getString("email"));
        TextView user_title=(TextView)findViewById(R.id.chatroom_user_title_textview);
        user_title.setText(bundle.get("name").toString());

        doj_n=bundle.get("doj").toString().trim();
        s_s=bundle.get("uid").toString().trim();
        daily_answer=(TextView)findViewById(R.id.daily_answer);
        database = FirebaseDatabase.getInstance();

       // ImageView image1=(ImageView)findViewById(R.id.chatroom_message_list_box_image_view);
       // ImageView image2=(ImageView)findViewById(R.id.chatroom_message_list_box_image_view1);
        mAuth = FirebaseAuth.getInstance();
        bundle=getIntent().getExtras();
        mfireBaseDatabase1 = FirebaseDatabase.getInstance();
        mFirebaseAuth1 = FirebaseAuth.getInstance();
        mFirebaseStorage1 = FirebaseStorage.getInstance();

//        imageView=(ImageView)findViewById(R.id.challange_imageview);
//        textView=(TextView)findViewById(R.id.challange_textview);

        String s1,s2,s;
        s1=firebaseUser.getUid().toString();
        s2=bundle.get("uid").toString();
//        if(s1.compareTo(s2)>=1)
//            s=s1+s2;
//        else s=s2+s1;



        f_flag=bundle.get("f_flag").toString().trim();


        if(f_flag.equals("0"))
        {
            s=s1+s2;
        }

        else
        {
            s=s2+s1;
        }










        mchatphoto1 = mFirebaseStorage1.getReference().child("imagestorage");
        mMessageDatabaseReference1 = mfireBaseDatabase1.getReference().child("chat").child(s);
       // mMessageDatabaseReference2 = mfireBaseDatabase1.getReference().child("chat").child(bundle.getString("uid")+firebaseUser.getUid());
        // mchatphoto1 = mFirebaseStorage1.getReference().child(number1);


        imageSend=(ImageButton)findViewById(R.id.photoPickerButton_chatroom);
       // titleSend=(EditText)findViewById(R.id.title_new_challenge_1);
        contentSend=(EditText)findViewById(R.id.messageEditText_chatroom);
        send=(Button)findViewById(R.id.sendButton_chatroom);




        mMessageListView = (ListView) findViewById(R.id.listview_chatroom);


        List<SendDetails> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new ChatRoomAdapter(this, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);





        daily_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag==0) {

                    if(f_flag.equals("0"))
                    {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        s_s_s=currentUser.getUid().toString().trim();
                    }
                    else
                    {
                        s_s_s=s_s;
                    }
                    myRef = database.getReference("data").child(s_s_s).child(doj_n).child("data_new");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            // String value = dataSnapshot.child("users").child(dataSnapshot.child("users").getKey()).child("train_name").toString();
                            //Log.d(TAG, "Value is: " + value);
                            //Toast.makeText(Received.this, value, Toast.LENGTH_SHORT).show();

                            //  option_list.clear();
                            flag=1;
                            SeatShareDetails sssd = dataSnapshot.getValue(SeatShareDetails.class);
                            TextView t1;
                            RelativeLayout rr1;
                            rr1=(RelativeLayout)findViewById(R.id.user_sharer_info);
                            rr1.setVisibility(View.VISIBLE);
                            rr1=(RelativeLayout)findViewById(R.id.user_chatbox);
                            rr1.setVisibility(View.GONE);
                            t1 = (TextView) findViewById(R.id.boarding_code);
                            t1.setText(sssd.getBoarding_code());
                            t1 = (TextView) findViewById(R.id.boarding_name);
                            t1.setText(sssd.getBoarding_name());
                            t1 = (TextView) findViewById(R.id.Class_code);
                            t1.setText(sssd.getClass_code());
                            t1 = (TextView) findViewById(R.id.destination_code);
                            t1.setText(sssd.getDestination_code());
                            t1 = (TextView) findViewById(R.id.destination_name);
                            t1.setText(sssd.getDestination_name());
                            t1 = (TextView) findViewById(R.id.doj);
                            t1.setText(sssd.getDoj());
                            t1 = (TextView) findViewById(R.id.price);
                            t1.setText(sssd.getPrice());
                            t1 = (TextView) findViewById(R.id.total_person);
                            t1.setText(sssd.getTotal_person()+"");
                            t1 = (TextView) findViewById(R.id.train_name);
                            t1.setText(sssd.getTrain_name());
                            t1 = (TextView) findViewById(R.id.train_number);
                            t1.setText(sssd.getTrain_number());
                            t1 = (TextView) findViewById(R.id.useremail);
                            t1.setText(sssd.getUser_email());
                            t1 = (TextView) findViewById(R.id.username);
                            t1.setText(sssd.getUser_name());
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            //Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }
                else
                {
                    flag=0;
                    RelativeLayout rr1;
                    rr1=(RelativeLayout)findViewById(R.id.user_sharer_info);
                    rr1.setVisibility(View.GONE);
                    rr1=(RelativeLayout)findViewById(R.id.user_chatbox);
                    rr1.setVisibility(View.VISIBLE);
                }

            }
        });
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Intent i = new Intent(More.this, NextActvity.class);
                //If you wanna send any data to nextActicity.class you can use
              //  i.putExtra(String key, value.get(position));

              //  startActivity(i);
               SendDetails send_image= (SendDetails)parent.getItemAtPosition(position);
               try {


                   if (!send_image.getChallange_image().equals(null)) {
                       Toast.makeText(chatroom.this, "Not NUll" + send_image.getChallange_image(), Toast.LENGTH_SHORT).show();
                       ImageView imageView_1=(ImageView)findViewById(R.id.chatroom_zoomin);
                       RelativeLayout relativeLayout_1=(RelativeLayout)findViewById(R.id.chatroom_zoomout);
                       imageView_1.setVisibility(View.VISIBLE);
                       relativeLayout_1.setVisibility(View.INVISIBLE);
                       Glide.with(imageView_1.getContext())
                               .load(send_image.getChallange_image())
                               .placeholder(R.mipmap.ic_launcher)
                               .into(imageView_1);
                   } else
                       Toast.makeText(chatroom.this, "Null", Toast.LENGTH_SHORT).show();
               }catch(Exception e)
               {
                   Toast.makeText(chatroom.this, "Text", Toast.LENGTH_SHORT).show();
               }

            }
        });

        // mUsername1 = name;
        mchildeventlistener1 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


               // pmsg=MyService.msg;
                SendDetails friend = dataSnapshot.getValue(SendDetails.class);
                mMessageAdapter.add(friend);
//                mMessageDatabaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //  MyService.msg++;
//                        //MyService.msg++;
////                        if(MyService.msg-pmsg>0){
////                            mMessageDatabaseReference2.push().setValue(MyService.msg-pmsg);
////
////                        }
//                        //  fl=1;
//                        //   System.out.println("We're done loading the initial "+dataSnapshot.getChildrenCount()+" items");
//                        //   startService(new Intent(getBaseContext(),MyService.class));
////                        Score score=new Score();
////                        score.sc=MyService.msg;
////                        score.psc=pmsg;
////                        score.scnumber=number;
////
////                        score.scnumber1=number2;
//
//                      //  mMessageDatabaseReference2.setValue(score);
////                    fl=0;
//                    }
//                    // public void onCancelled(FirebaseException firebaseError) { }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
                //   MyService.msg-=pmsg;



//                if(MyService.msg>pmsg) {
//                //    MyService.msg = dataSnapshot.getChildrenCount();

//                }
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





        imageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

                // TODO: Fire an intent to show an image picker
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                //my = user1.getPhoneNumber();


                SendDetails friendlyMessage = new SendDetails();
                friendlyMessage.setChallenge_content(contentSend.getText().toString());
               // friendlyMessage.setChallenge_name(titleSend.getText().toString());
                friendlyMessage.setChallenge_sender_id(firebaseUser.getUid());
                friendlyMessage.setChallenge_sender_name(firebaseUser.getDisplayName());
                friendlyMessage.setSender_email(firebaseUser.getEmail());
                friendlyMessage.setChallenge_v("1");
                friendlyMessage.setChallenge_receiver_id(bundle.getString("uid"));
                friendlyMessage.setChallenge_receiver_name(bundle.getString("name"));
                friendlyMessage.setReceiver_email(bundle.getString("email"));
                mMessageDatabaseReference1.push().setValue(friendlyMessage);
               // mMessageDatabaseReference2.push().setValue(friendlyMessage);
                // Clear input box
//                boolean isPhoto = friendlyMessage.getChallange_image() != null;
//                if (isPhoto) {
//                    //.setVisibility(View.GONE);
//                    imageView.setVisibility(View.VISIBLE);
//                    //messageTextView1.setVisibility(View.GONE);
//                    //imageView.setVisibility(View.VISIBLE);
//                    Glide.with(imageView.getContext())
//                            .load(friendlyMessage.getChallange_image())
//                            .into(imageView);
////                        Glide.with(photoImageView1.getContext())
////                                .load(message.getPhotoUrl())
////                                .into(photoImageView1);
//                }
//                textView.setText(contentSend.getText().toString());
                contentSend.setText("");
//                Intent intent=new Intent(ChallenegeForm.this,sent.class);
//                startActivity(intent);

            }
        });


    }

}
