package com.example.youtubex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChannel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Global");

        Button channel_id_submit =(Button) findViewById(R.id.Sumbit);
        final EditText channel = (EditText) findViewById(R.id.channelId);

        Bundle bundle = getIntent().getExtras();
        final String userName = bundle.getString("userName");
        final String url = bundle.getString("url");
        channel_id_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] coin = new String[1];
                final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());
                dataref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        coin[0] = dataSnapshot.getValue(String.class);

                        int Coin = Integer.parseInt(coin[0]);

                        if(Coin>0) {
                            if (!channel.getText().equals("")) {


                                String channelId = getChannelId(channel.getText().toString());


                                Coin = Coin - 1;
                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef1 = database1.getReference("UserData").child(MainActivity.user.getUid());
                                myRef1.child("Coin").setValue(Coin+"");
                                MainActivity.coin.setText("Coin : "+Coin);

                                myRef.child("Subscribe").child(channelId).child("userName").setValue(userName);
                                myRef.child("Subscribe").child(channelId).child("profileurl").setValue(url);
                                Toast.makeText(getApplicationContext(), "ADD TO SUBSCRIBE", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "You Do Not Have Any Coin Plsease Subscribe/Like/View others Channel To get Coin", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    String getChannelId(String channelurl){
       return channelurl.substring( channelurl.lastIndexOf('/')+1);
    }

}

