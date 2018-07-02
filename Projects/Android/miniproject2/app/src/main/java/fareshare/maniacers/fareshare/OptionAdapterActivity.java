package fareshare.maniacers.fareshare;

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

import fareshare.maniacers.fareshare.R;
import fareshare.maniacers.fareshare.SeatShareDetails;

public class OptionAdapterActivity extends ArrayAdapter<SeatShareDetails> {
    private Activity context;
    private List<SeatShareDetails> ssList;
    String disp = "No of Guests Visiting : ";
    private DatabaseReference databaseReferenceUsers;


    public OptionAdapterActivity(Activity context, List<SeatShareDetails> ssList)
    {
        super(context, R.layout.content_option_adapter, ssList);
        this.context = context;
        this.ssList = ssList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.content_option_adapter, null, true);

        TextView textViewNameOfSharer = (TextView) listViewItem.findViewById(R.id.OAA_name_of_sharer);
        TextView textViewPriceExpected = (TextView) listViewItem.findViewById(R.id.price_expected);
        TextView textViewSeatDetails = (TextView) listViewItem.findViewById(R.id.seat_details);
        TextView textViewDOJ = (TextView) listViewItem.findViewById(R.id.doj);

        SeatShareDetails ssdetails = ssList.get(position);

        textViewNameOfSharer.setText(ssdetails.getUser_name());
        textViewPriceExpected.setText(ssdetails.getPrice());
        textViewSeatDetails.setText(ssdetails.getDestination_name());
        textViewDOJ.setText(ssdetails.getDoj());

        return  listViewItem;
    }
}