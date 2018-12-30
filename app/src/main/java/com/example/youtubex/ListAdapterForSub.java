package com.example.youtubex;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapterForSub extends RecyclerView.Adapter<ListAdapterForSub.ViewHolder> {


    Activity activity;
    ArrayList<String> channellist;
    ArrayList<String> channelidlist;
    ArrayList<String> url;
    ArrayList<String> viewHistory;
    RecyclerView recyclerView;
    Context context;

    String channelID;


    public void update(String channel, String channelid,String imgurl) {
        channellist.add(channel);
        channelidlist.add(channelid);
        url.add(imgurl);
        notifyDataSetChanged();// refresh recyler view
    }

    public  void  updateHistory(String view)
    {
        viewHistory.add(view);
    }

    public ListAdapterForSub(RecyclerView recyclerView, Context context, ArrayList<String> channellist, ArrayList<String> channelidlist, ArrayList<String> url, ArrayList<String> viewHistory) {

        this.recyclerView = recyclerView;
        this.context = context;
        this.channellist = channellist;
        this.channelidlist = channelidlist;
        this.url = url;
        this.viewHistory=viewHistory;

    }


    @NonNull
    @Override
    public ListAdapterForSub.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_items, parent, false);
        return new ListAdapterForSub.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterForSub.ViewHolder holder, int position) {
        String channel=channellist.get(position);
        holder.channel_name.setText(channel);
        Glide.with(context).load(url.get(position)).into(holder.videopic);
    }

    @Override
    public int getItemCount() {
        return channellist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton viewButton;
        public ImageView videopic;
        public TextView video_id,channel_name ,channel;

        public ViewHolder(final View itemView) {    // repres idv items view
            super(itemView);
            viewButton = itemView.findViewById(R.id.subButton);
            channel = itemView.findViewById(R.id.viewsCount);
            channel_name = itemView.findViewById(R.id.channel_name);
            videopic = itemView.findViewById(R.id.video_image2);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    Intent i = new Intent(context,WebPage.class);
                    i.putExtra("ForClass","ForSub");
                    i.putExtra("URL",channelidlist.get(position));

                    getCoin(channelidlist.get(position));

                    context.startActivity(i);
                }
            });
        }
    }


    void getCoin(String Channel){
        channelID = Channel;
        final String[] coin = new String[1];
        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());


        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(!viewHistory.contains(channelID)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User").child(MainActivity.user.getUid());
                    myRef.child("Sub").child(channelID).setValue("1");

                    coin[0] = dataSnapshot.getValue(String.class);
                    int Coin;
                    Coin = Integer.parseInt(coin[0]);

                    Coin = Coin + 1;
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("UserData").child(MainActivity.user.getUid());
                    myRef.child("Coin").setValue(Coin + "");

                    MainActivity.coin.setText("Coin : " + Coin);
                }
                else{
                    Toast.makeText(context,"ALREADY Viewed",Toast.LENGTH_SHORT).show();
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
}

