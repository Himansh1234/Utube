package com.example.youtubex;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class VideoListAdapter_Database extends RecyclerView.Adapter<VideoListAdapter_Database.ViewHolder> {


    Activity activity;
    ArrayList<VideoDetails> singletons;
    RecyclerView recyclerView;
    Context context;
    String video;

    ArrayList<String> viewHistory ;


    public void update(VideoDetails videoDetails) {
        singletons.add(videoDetails);
        notifyDataSetChanged();// refresh recyler view
    }


    public  void  updateHistory(String view)
    {
        viewHistory.add(view);
    }

    public VideoListAdapter_Database(RecyclerView recyclerView, Context context, ArrayList<VideoDetails> singletons,ArrayList<String> viewHistory) {

        this.recyclerView = recyclerView;
        this.context = context;
        this.singletons = singletons;
        this.viewHistory=viewHistory;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       VideoDetails videoDetails=singletons.get(position);
        holder.video_name.setText(videoDetails.getVideoName());
        holder.video_views.setText("Views : "+videoDetails.getViewsCount());
        Glide.with(context).load(videoDetails.getURL()).into(holder.videopic);

    }

    @Override
    public int getItemCount() {
        return singletons.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton viewButton;
        ImageView videopic;
        public TextView video_id,video_name ,video_views;

        public ViewHolder(final View itemView) {    // repres idv items view
            super(itemView);
            viewButton = itemView.findViewById(R.id.viewButton);
            video_name = itemView.findViewById(R.id.view_video_title);
            video_views = itemView.findViewById(R.id.viewsCount);
            videopic = itemView.findViewById(R.id.video_image);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    Intent i = new Intent(context,VideoActivity.class);
                    i.putExtra("videoId",singletons.get(position).getVideoId());
                    i.putExtra("videoName",singletons.get(position).getVideoName());
                    i.putExtra("videodec",singletons.get(position).getVideoDesc());
                    i.putExtra("videoView",singletons.get(position).getViewsCount());
                    getCoin(singletons.get(position).getVideoId());
                    context.startActivity(i);

                }
            });

        }
    }


    void getCoin( String videoid){

        video = videoid;



        final String[] coin = new String[1];
        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());
        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(!viewHistory.contains(video)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User").child(MainActivity.user.getUid());
                    myRef.child("Views").child(video).setValue("1");
                    coin[0] = dataSnapshot.getValue(String.class);
                    int Coin = Integer.parseInt(coin[0]);
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
