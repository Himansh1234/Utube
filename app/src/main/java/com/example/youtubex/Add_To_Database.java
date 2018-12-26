package com.example.youtubex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_To_Database extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__to__database);

        Button views = findViewById(R.id.add_view);
        Button like = findViewById(R.id.add_like);
        Button suscribe = findViewById(R.id.add_suscribes);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Global");


        final String videoID ,videoName, videodescp;

        Bundle bundle = getIntent().getExtras();
        videoID =bundle.getString("videoId");
        videoName = bundle.getString("videoName");
        videodescp = bundle.getString("videodec");

        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("Views").child(videoID).child("id").setValue(videoID);
                myRef.child("Views").child(videoID).child("name").setValue(videoName);
                myRef.child("Views").child(videoID).child("description").setValue(videodescp);
                Toast.makeText(getApplicationContext(),"ADD TO VIEWS",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("Likes").child("VideoID").setValue(videoID);
                Toast.makeText(getApplicationContext(),"ADD TO LIKES",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MyVideos.class);
                startActivity(i);
            }
        });

        suscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("Suscribes").child("VideoID").setValue(videoID);
                Toast.makeText(getApplicationContext(),"ADD TO SUSCRIBES",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MyVideos.class);
                startActivity(i);
            }
        });

    }
}
