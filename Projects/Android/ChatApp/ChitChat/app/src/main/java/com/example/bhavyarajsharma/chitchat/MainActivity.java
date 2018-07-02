package com.example.bhavyarajsharma.chitchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    EditText e;
    String s,p,t;
    FirebaseAuth fa;
    FirebaseUser fu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNextScreen = (Button) findViewById(R.id.button);
        final Intent intent = new Intent(this,homepage.class);

        sharedpreferences =getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        final SharedPreferences.Editor ed=sharedpreferences.edit();
        //Listening to button event
       if(sharedpreferences.getString("login","").equals("y")){
           startActivity(intent);
       }
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent





                e=(EditText)findViewById(R.id.email_edit);
                s=e.getText().toString();

                ed.putString("user", s);
                ed.putString("login","y");

                ed.apply();
                startActivity(intent);}});


    }
}
