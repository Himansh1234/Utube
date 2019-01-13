package com.example.youtubex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_To_Database extends AppCompatActivity {
//1998
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__to__database);

        Button views = findViewById(R.id.add_view);
        Button like = findViewById(R.id.add_like);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Global");


        final String videoID ,videoName, videodescp,imageurl;

        Bundle bundle = getIntent().getExtras();
        videoID =bundle.getString("videoId");
        videoName = bundle.getString("videoName");
        videodescp = bundle.getString("videodec");
        imageurl = bundle.getString("videourl");

        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] coin = new String[1];
                final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());
                dataref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        coin[0] = dataSnapshot.getValue(String.class);

                        int Coin = Integer.parseInt(coin[0]);

                        if (Coin > 0) {

                            if (videodescp != null && videoID != null && videoName != null && imageurl != null) {

                                Coin = Coin - 1;
                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef1 = database1.getReference("UserData").child(MainActivity.user.getUid());
                                myRef1.child("Coin").setValue(Coin+"");
                                MainActivity.coin.setText("Coin : "+Coin);

                                myRef.child("Views").child(videoID).child("id").setValue(videoID);
                                myRef.child("Views").child(videoID).child("name").setValue(videoName);
                                myRef.child("Views").child(videoID).child("description").setValue(videodescp);
                                myRef.child("Views").child(videoID).child("url").setValue(imageurl);

                                Toast.makeText(getApplicationContext(), "ADD TO VIEWS", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "PLSEASE ADD AGAIN", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You Do Not Have Any Coin Plsease Subscribe/Like/View others Channel To get Coin", Toast.LENGTH_SHORT).show();


                        }
                    }

                        @Override
                        public void onChildChanged (@NonNull DataSnapshot
                        dataSnapshot, @Nullable String s){
                        }

                        @Override
                        public void onChildRemoved (@NonNull DataSnapshot dataSnapshot){

                        }

                        @Override
                        public void onChildMoved (@NonNull DataSnapshot
                        dataSnapshot, @Nullable String s){

                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError){

                        }

                });

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] coin = new String[1];
                final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());
                dataref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        coin[0] = dataSnapshot.getValue(String.class);

                        int Coin = Integer.parseInt(coin[0]);

                        if (Coin > 0) {

                            if(videodescp!=null && videoID!=null && videoName !=null && imageurl!=null) {
                                Coin = Coin - 1;
                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef1 = database1.getReference("UserData").child(MainActivity.user.getUid());
                                myRef1.child("Coin").setValue(Coin+"");
                                MainActivity.coin.setText("Coin : "+Coin);

                                myRef.child("Likes").child(videoID).setValue(videoID);
                                myRef.child("Likes").child(videoID).child("name").setValue(videoName);
                                myRef.child("Likes").child(videoID).child("description").setValue(videodescp);
                                myRef.child("Likes").child(videoID).child("url").setValue(imageurl);
                                Toast.makeText(getApplicationContext(), "ADD TO LIKES", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "PLSEASE ADD AGAIN", Toast.LENGTH_SHORT).show();


                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You Do Not Have Any Coin Plsease Subscribe/Like/View others Channel To get Coin", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onChildChanged (@NonNull DataSnapshot
                                                        dataSnapshot, @Nullable String s){
                    }

                    @Override
                    public void onChildRemoved (@NonNull DataSnapshot dataSnapshot){

                    }

                    @Override
                    public void onChildMoved (@NonNull DataSnapshot
                                                      dataSnapshot, @Nullable String s){

                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }

                });

            }
        });



    }
}
