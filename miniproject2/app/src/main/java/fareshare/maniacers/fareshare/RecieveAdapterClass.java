package fareshare.maniacers.fareshare;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhavya Raj Sharma on 29-04-2018.
 */

public class RecieveAdapterClass extends ArrayAdapter <SendData>
{
    private Activity context;
    private List<SendData> ssList;
    private DatabaseReference databaseReferenceUsers;

    public RecieveAdapterClass(Activity context, List<SendData> ssList)
    {
        super(context, R.layout.recieve_adapter_layout, ssList);
        this.context = context;
        this.ssList = ssList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.recieve_adapter_layout, null, true);

        TextView textViewNameOfSender = (TextView) listViewItem.findViewById(R.id.RAA_sender_name);
        TextView textViewSenderEmail = (TextView) listViewItem.findViewById(R.id.RAA_sender_email);


        SendData sendData = ssList.get(position);

        textViewNameOfSender.setText(sendData.getSend_name());
        textViewSenderEmail.setText(sendData.getSend_email());

        return  listViewItem;
    }

}
