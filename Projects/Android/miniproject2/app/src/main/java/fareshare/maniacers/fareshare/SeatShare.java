package fareshare.maniacers.fareshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SeatShare extends AppCompatActivity {

    Button button;
    EditText edit_price,edit_pnr,my_user_name;
    public static TextView textView;
    public static String userpnr;
    public static String user__id;
    public static String user__email;

    public static String getUser__id() {
        return user__id;
    }

    public static void setUser__id(String user__id) {
        SeatShare.user__id = user__id;
    }

    public static String getUser__email() {
        return user__email;
    }

    public static void setUser__email(String user__email) {
        SeatShare.user__email = user__email;
    }

    public static  String getUserpnr() {
        return userpnr;
    }
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_share);

        textView = (TextView)findViewById(R.id.seatshare_text_data);
        button= (Button)findViewById(R.id.seatshare_button);
        edit_price= (EditText) findViewById(R.id.seatshare_edit_price);
        edit_pnr= (EditText) findViewById(R.id.seatshare_edit_pnr);
        my_user_name=(EditText)findViewById(R.id.seatshare_edit_name);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userpnr=edit_pnr.getText().toString().trim();
                user__email=currentUser.getEmail().toString();
                user__id=currentUser.getUid().toString();
                Toast.makeText(SeatShare.this, "Fetching Data", Toast.LENGTH_SHORT).show();
                FetchData process = new FetchData(edit_pnr.getText().toString(),edit_price.getText().toString(),my_user_name.getText().toString());
                process.execute();


                //textView.setText(process.getData());

            }
        });





    }
}
