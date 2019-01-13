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


public class ListAdapterForLikes extends RecyclerView.Adapter<ListAdapterForLikes.ViewHolder> {


    Activity activity;
    ArrayList<VideoDetails> singletons;
    RecyclerView recyclerView;
    Context context;
    String videoid;

    ArrayList<String> viewHistory ;

    public void update(VideoDetails videoDetails) {
        singletons.add(videoDetails);
        notifyDataSetChanged();// refresh recyler view
    }

    public  void  updateHistory(String view)
    {
        viewHistory.add(view);
    }

    public ListAdapterForLikes(RecyclerView recyclerView, Context context, ArrayList<VideoDetails> singletons, ArrayList<String> viewHistory) {

        this.recyclerView = recyclerView;
        this.context = context;
        this.singletons = singletons;
        this.viewHistory=viewHistory;


    }


    @NonNull
    @Override
    public ListAdapterForLikes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.likes_view, parent, false);
        return new ListAdapterForLikes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterForLikes.ViewHolder holder, int position) {
        VideoDetails videoDetails=singletons.get(position);
        holder.video_name.setText(videoDetails.getVideoName());
        if(videoDetails.getLikesCount()!=null)
            holder.video_views.setText("Likes : "+videoDetails.getLikesCount());
        if(videoDetails.getURL()!=null)
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
            viewButton = itemView.findViewById(R.id.viewButton1);
            video_name = itemView.findViewById(R.id.view_video_title1);
            video_views = itemView.findViewById(R.id.viewsCount1);
            videopic = itemView.findViewById(R.id.video_image1);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    VideoDetails videoDetails = singletons.get(position);
                    Intent i = new Intent(context,WebPage.class);
                    i.putExtra("ForClass","ForLike");
                    i.putExtra("videoid",videoDetails.getVideoId());

                    getCoin(videoDetails.getVideoId());


                    context.startActivity(i);

                }
            });

        }
    }


    void getCoin(String video){

        videoid= video;
        final String[] coin = new String[1];
        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("UserData").child(MainActivity.user.getUid());
        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!viewHistory.contains(videoid)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User").child(MainActivity.user.getUid());
                    myRef.child("Like").child(videoid).setValue("1");
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

