package fareshare.maniacers.fareshare;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhavya Raj Sharma on 18-04-2018.
 */

public class FetchData extends AsyncTask<Void,Void,Void> {
    static String user_pnr;
    static String p_price;
    static String user_n;

    public FetchData(String s,String ss,String un) {
        user_pnr=s;p_price=ss;
        user_n=un;
    }

    public String getPnrno() {
        return pnrno;
    }
    SeatShareDetails ssd =new SeatShareDetails();



    String pnrno="https://api.railwayapi.com/v2/pnr-status/pnr/"+SeatShare.getUserpnr()+"/apikey/9stmnz7b96/";
    //String route_url="https://api.railwayapi.com/v2/route/train/"+SeatShare.getUserpnr()+"/apikey/9stmnz7b96/";
     String data="";
    // String dataparsed="";
    //String singleparsed="";
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SeatShare.textView.setText(this.data);
        try {
            dataParsing(this.data);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url=new URL(pnrno);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }
//            JSONArray jsonArray=new JSONArray(data);
//            for (int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
//                singleparsed=""
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return null;
    }

//    public String getData() {
//
//        return data;
//    }







    public void dataParsing(String data) throws JSONException {
        JSONObject jsonObj = new JSONObject(data);

        // Getting JSON Array node
        JSONObject ts = jsonObj.getJSONObject("reservation_upto");
        ssd.setDestination_name(ts.getString("name"));
        ssd.setDestination_code(ts.getString("code"));


        ts = jsonObj.getJSONObject("train");
        ssd.setTrain_name(ts.getString("name"));
        ssd.setTrain_number(ts.getString("number"));

        ts = jsonObj.getJSONObject("journey_class");
        ssd.setClass_code(ts.getString("code"));

        ts = jsonObj.getJSONObject("from_station");
        ssd.setBoarding_name(ts.getString("name"));
        ssd.setBoarding_code(ts.getString("code"));


        ssd.setTotal_person(jsonObj.getInt("total_passengers"));


        ssd.setDoj(jsonObj.getString("doj"));

//        String[] current=new String[100];
        List<String> current_ = new ArrayList<String>();
//        String[] booking=new String[100];
        List<String> bookin_ = new ArrayList<String>();
        JSONArray ja = jsonObj.getJSONArray("passengers");
        for (int i = 0; i < ja.length(); i++) {

            ts=ja.getJSONObject(i);
            current_.add(ts.getString("current_status"));
            bookin_.add(ts.getString("booking_status"));
        //    current[i] = ts.getString("current_status");
          //  booking[i] = ts.getString("booking_status");
        }
       // ssd.setCurrent_status(current);
       // ssd.setBooking_status(booking);
        ssd.setCurrent(current_);
        ssd.setBooking(bookin_);

        ssd.setUser_name(user_n);
        ssd.setPrice(p_price);
        ssd.setUser_email(SeatShare.getUser__email().toString());
        ssd.setUser_id(SeatShare.getUser__id().toString());
        if(!(ssd.getTrain_name()=="null"))
        {SeatShare.textView.setText("Request Uploaded");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ssd.getTrain_number().toString().trim()).child("users").child(ssd.getUser_id());
            DatabaseReference myRef1 = database.getReference("data").child(ssd.getUser_id()).child(ssd.getDoj()).child("data_new");
            myRef1.setValue(ssd);
        myRef.setValue(ssd);}
 else SeatShare.textView.setText("Wrong PNR");
    }















}
