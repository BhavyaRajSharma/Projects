package fareshare.maniacers.fareshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Received extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef,myref2;

    private ListView listViewSSDetails;

    private List<SendData> option_list;
    SendData ssd=new SendData();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String receiver_id,receiver_name,doj,getReceiver_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);



        database= FirebaseDatabase.getInstance();
        option_list = new ArrayList<SendData>();
        listViewSSDetails = (ListView) findViewById(R.id.ListView1);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();




        listViewSSDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), chatroom.class);

                receiver_id=option_list.get(position).getSend_email().toString();
                receiver_name=option_list.get(position).getSend_name().toString();
                getReceiver_uid=option_list.get(position).getSend_uid().toString();
                doj=option_list.get(position).getDoj().toString().trim();
                intent.putExtra("name",receiver_name);
                intent.putExtra("email",receiver_id);
                intent.putExtra("uid",getReceiver_uid);
                intent.putExtra("doj",doj);
                intent.putExtra("f_flag","0");
                //intent.putExtra("NUMBER",friendlyMessages.get(position).getNumber());
//                TextView textView=(TextView)findViewById(R.id.notify);
//                textView.setText("");

                startActivity(intent);

            }
        });





        //EditText editText=(EditText)findViewById(R.id.search_train);
        myRef = database.getReference("send_request");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

               // String value = dataSnapshot.child("users").child(dataSnapshot.child("users").getKey()).child("train_name").toString();
                //Log.d(TAG, "Value is: " + value);
                //Toast.makeText(Received.this, value, Toast.LENGTH_SHORT).show();

                option_list.clear();
                for(DataSnapshot eventSnapshot :dataSnapshot.getChildren())
                {
                    SendData ssdetails = eventSnapshot.getValue(SendData.class);
                    if(!(ssdetails.getSend_uid().equals(currentUser.getUid())))
                    option_list.add(ssdetails);
                }

                RecieveAdapterClass recieveAdapterClass = new RecieveAdapterClass(Received.this, option_list);
                listViewSSDetails.setAdapter(recieveAdapterClass);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }







}
