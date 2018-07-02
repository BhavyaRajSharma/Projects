package fareshare.maniacers.fareshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchSeat extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef,myref2;

    private ListView listViewSSDetails;

    private List<SeatShareDetails> option_list;
    SeatShareDetails ssd=new SeatShareDetails();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String receiver_id,receiver_name,getReceiver_uid,doj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seat);
        database= FirebaseDatabase.getInstance();
        option_list = new ArrayList<SeatShareDetails>();
        listViewSSDetails = (ListView) findViewById(R.id.ListView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        listViewSSDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), chatroom.class);

                receiver_id=option_list.get(position).getUser_id().toString();
                receiver_name=option_list.get(position).getUser_name().toString();
                getReceiver_uid=option_list.get(position).getUser_id().toString();
                intent.putExtra("name",receiver_name);
                doj=option_list.get(position).getDoj().toString().trim();
                intent.putExtra("email",receiver_id);
                intent.putExtra("uid",getReceiver_uid);
                intent.putExtra("doj",doj);
                intent.putExtra("f_flag","1");
                myref2=database.getReference("send_request").child(getReceiver_uid);
                SendData sd= new SendData();
                sd.setSend_email(currentUser.getEmail().toString());
                sd.setSend_name(currentUser.getDisplayName().toString());
                sd.setSend_uid(currentUser.getUid().toString());
                sd.setDoj(doj);
                myref2.setValue(sd);

                //intent.putExtra("NUMBER",friendlyMessages.get(position).getNumber());
//                TextView textView=(TextView)findViewById(R.id.notify);
//                textView.setText("");

                startActivity(intent);

            }
        });


    }

    public void searchButtonSeat(View view) {

        EditText editText=(EditText)findViewById(R.id.search_train);
        myRef = database.getReference(editText.getText().toString().trim()).child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

               // String value = dataSnapshot.child("users").child(dataSnapshot.child("users").getKey()).child("train_name").toString();
                //Log.d(TAG, "Value is: " + value);
               // Toast.makeText(SearchSeat.this, value, Toast.LENGTH_SHORT).show();

                option_list.clear();
                for(DataSnapshot eventSnapshot :dataSnapshot.getChildren())
                {
                    SeatShareDetails ssdetails = eventSnapshot.getValue(SeatShareDetails.class);
                    if(!(ssdetails.getUser_id().equals(currentUser.getUid()))) {
                     EditText e=(EditText)findViewById(R.id.search_train_naya);
                     if(e.getText().toString().trim().equals(ssdetails.getDoj().toString().trim()))
                        option_list.add(ssdetails);
                    }
                }

                OptionAdapterActivity optionAdapterActivity = new OptionAdapterActivity(SearchSeat.this, option_list);
                listViewSSDetails.setAdapter(optionAdapterActivity);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
