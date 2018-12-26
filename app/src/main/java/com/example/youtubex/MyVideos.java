package com.example.youtubex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;


public class MyVideos  extends AppCompatActivity {

   EditText channelID;
   Button Sumbit;
   FirebaseAuth mAuth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_my_videos);
       {

         /*  mAuth = FirebaseAuth.getInstance();
           channelID = findViewById(R.id.channelId);
           Sumbit = findViewById(R.id.Sumbit);*/

       }


       Button channel_id_submit =(Button) findViewById(R.id.Sumbit);
       final EditText channel = (EditText) findViewById(R.id.channelId);

       channel_id_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MyVideos.this,MyVideosList.class);
               i.putExtra("Channel_ID",channel.getText().toString());
               startActivity(i);
           }
       });
   }

}