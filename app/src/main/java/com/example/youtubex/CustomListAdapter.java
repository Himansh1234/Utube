package com.example.youtubex;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;


public class CustomListAdapter extends BaseAdapter {

    Activity activity;

    ImageLoader imageLoader;// = AppController.getInstance().getImageLoader();

    private LayoutInflater inflater;

    ArrayList<VideoDetails> singletons;



    public CustomListAdapter(Activity activity, ArrayList<VideoDetails> singletons) {

        this.activity = activity;

        this.singletons = singletons;

    }



    public int getCount() {

        return this.singletons.size();

    }



    public Object getItem(int i) {

        return this.singletons.get(i);

    }



    public long getItemId(int i) {

        return (long) i;

    }



    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        if (this.inflater == null) {

            this.inflater = (LayoutInflater) this.activity.getLayoutInflater();

            // getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        if (convertView == null) {

            convertView = this.inflater.inflate(R.layout.item, null);

        }

        if (this.imageLoader == null) {

//            this.imageLoader = AppController.getInstance().getImageLoader();

        }

        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.video_image);

        final TextView imgtitle = (TextView) convertView.findViewById(R.id.video_title);


        // final TextView tvURL=(TextView)convertView.findViewById(R.id.tv_url);

        final  TextView tvVideoID=(TextView)convertView.findViewById(R.id.video_id);


        final View finalConvertView = convertView;
        ((CardView) convertView.findViewById(R.id.cardView)).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                VideoDetails singleton = (VideoDetails) getItem(i);

                Intent intent=new Intent(view.getContext(), Add_To_Database.class);

                intent.putExtra("videoId",tvVideoID.getText().toString());
                intent.putExtra("videoName",imgtitle.getText().toString());
                intent.putExtra("videodec",singleton.getVideoDesc());

                finalConvertView.getContext().startActivity(intent);
                activity.finish();



            }

        });



        Toast.makeText(convertView.getContext(),"CUST",Toast.LENGTH_SHORT).show();



        VideoDetails singleton = (VideoDetails) this.singletons.get(i);

//        networkImageView.setImageUrl(singleton.getURL(), this.imageLoader);

        tvVideoID.setText(singleton.getVideoId());

        imgtitle.setText(singleton.getVideoName());

        //imgdesc.setText(singleton.getVideoDesc());

        return convertView;

    }

}
