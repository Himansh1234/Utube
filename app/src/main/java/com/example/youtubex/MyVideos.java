package com.example.youtubex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

            mAuth = FirebaseAuth.getInstance();
            channelID = findViewById(R.id.channelId);
            Sumbit = findViewById(R.id.Sumbit);

        }

    }

    public void Sumbit(View view) {

        String Id = channelID.getText().toString().trim();
        Intent intent = new Intent(MyVideos.this,Recycler_channelList.class);
        intent.putExtra(Id,"ChannelId");
        startActivity(intent);


    }

}