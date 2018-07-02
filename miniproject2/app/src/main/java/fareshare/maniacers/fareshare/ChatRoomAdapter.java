package fareshare.maniacers.fareshare;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Bhavya Raj Sharma on 06-12-2017.
 */

public class ChatRoomAdapter extends ArrayAdapter<SendDetails> {

    Activity context;
    List<SendDetails> send;



    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    public ChatRoomAdapter(Activity context, List<SendDetails> send)
    {
        super(context,R.layout.chatroom_listadapter,send);
        this.context=context;
        this.send=send;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.chatroom_listadapter, null, true);
        TextView message1=(TextView)listViewItem.findViewById(R.id.chatroom_message_list_box_textview);
        TextView message2=(TextView)listViewItem.findViewById(R.id.chatroom_message_list_box_textview1);
        ImageView image1=(ImageView)listViewItem.findViewById(R.id.chatroom_message_list_box_image_view);
        ImageView image2=(ImageView)listViewItem.findViewById(R.id.chatroom_message_list_box_image_view1);

        //TextView sender_person=(TextView)listViewItem.findViewById(R.id.message_sender_id_textview);


        SendDetails sendDetails=send.get(position);
        if(!sendDetails.getChallenge_content().equals("")) {
            if (sendDetails.getChallenge_sender_id().equals(firebaseUser.getUid())) {
                message2.setVisibility(View.GONE);
                message1.setVisibility(View.VISIBLE);
            } else {
                message1.setVisibility(View.GONE);
                message2.setVisibility(View.VISIBLE);
            }

            message1.setText(sendDetails.getChallenge_content());
            message2.setText(sendDetails.getChallenge_content());
            image1.setVisibility(View.GONE);
            image2.setVisibility(View.GONE);
        }
        else
        {
            Glide.with(image1.getContext())
                    .load(sendDetails.getChallange_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(image1);

            Glide.with(image2.getContext())
                    .load(sendDetails.getChallange_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(image2);
            if(sendDetails.getChallenge_content().equals(""))
            {
                if (sendDetails.getChallenge_sender_id().equals(firebaseUser.getUid())) {
                    image2.setVisibility(View.GONE);
                    image1.setVisibility(View.VISIBLE);
                }
                else
                {
                    image1.setVisibility(View.GONE);
                    image2.setVisibility(View.VISIBLE);
                }

            }

            message1.setVisibility(View.GONE);
            message2.setVisibility(View.GONE);
        }
//        if(firebaseUser.getEmail().equals(sendDetails.getSender_email()))
//            challenge_date.setText(sendDetails.getReceiver_email());
//        else challenge_date.setText(sendDetails.getSender_email());
        return listViewItem;


    }
}
