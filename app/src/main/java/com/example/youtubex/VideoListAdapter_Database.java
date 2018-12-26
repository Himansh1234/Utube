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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class VideoListAdapter_Database extends RecyclerView.Adapter<VideoListAdapter_Database.ViewHolder> {


    Activity activity;
    ArrayList<VideoDetails> singletons;
    RecyclerView recyclerView;
    Context context;


    public void update(VideoDetails videoDetails) {
        singletons.add(videoDetails);
        notifyDataSetChanged();// refresh recyler view
    }



    public VideoListAdapter_Database(RecyclerView recyclerView, Context context, ArrayList<VideoDetails> singletons) {

        this.recyclerView = recyclerView;
        this.context = context;
        this.singletons = singletons;

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

    }

    @Override
    public int getItemCount() {
        return singletons.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton viewButton;
        public TextView video_id,video_name ,video_views;

        public ViewHolder(final View itemView) {    // repres idv items view
            super(itemView);
            viewButton = itemView.findViewById(R.id.viewButton);
            video_name = itemView.findViewById(R.id.view_video_title);
            video_views = itemView.findViewById(R.id.viewsCount);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    Intent i = new Intent(context,VideoActivity.class);
                    i.putExtra("videoId",singletons.get(position).getVideoId());
                    i.putExtra("videoName",singletons.get(position).getVideoName());
                    i.putExtra("videodec",singletons.get(position).getVideoDesc());
                    i.putExtra("videoView",singletons.get(position).getViewsCount());
                    context.startActivity(i);
                }
            });

        }
    }



}
