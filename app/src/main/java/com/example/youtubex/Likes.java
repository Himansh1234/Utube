package com.example.youtubex;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Likes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Likes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Likes extends Fragment {                                                        //AUTO GENRATE DATA
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<VideoDetails> ArrayVideoList;
    String videoID,videoName="",videodec="";
    RecyclerView lvVideo;
    ListAdapterForLikes listAdapterForLikes;
    Context context;


    public Likes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Likes.
     */
    // TODO: Rename and change types and number of parameters
    public static Likes newInstance(String param1, String param2) {
        Likes fragment = new Likes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        ArrayVideoList= new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDataFromFirebase();
        getHistory();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likes, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        /////////////////////////////////////////////////////////////////    FIREBASE RETRIVE
        lvVideo=  getActivity().findViewById(R.id.like_videoList);

        lvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapterForLikes = new ListAdapterForLikes(lvVideo, getContext(), new ArrayList<VideoDetails>(),new ArrayList<String>());
        lvVideo.setAdapter(listAdapterForLikes);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    void getDataFromFirebase(){


        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Global").child("Likes");
        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //call indv items


                final String videoID = dataSnapshot.getKey();
                final  String videoName = dataSnapshot.child("name").getValue(String.class);
                final String videodec = dataSnapshot.child("description").getValue(String.class);
                final  String imageurl = dataSnapshot.child("url").getValue(String.class);

                String url = "https://www.googleapis.com/youtube/v3/videos?part=statistics&id="+videoID+"&key=AIzaSyC1zuY8lLZ3xDjGvrbN7SNcWEJxJEE1YiI";



                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());

                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);

                            JSONArray jsonArray=jsonObject.getJSONArray("items");
                            JSONObject jsonitem = jsonArray.getJSONObject(0);

                            JSONObject jsonObjectstata = jsonitem.getJSONObject("statistics");

                            VideoDetails videoDetails=new VideoDetails();

                            videoDetails.setLikesCount(jsonObjectstata.getString("likeCount")+"");
                            videoDetails.setVideoId(videoID);
                            videoDetails.setVideoName(videoName);
                            videoDetails.setVideoDesc(videodec);
                            videoDetails.setURL(imageurl);

                            if(videodec!=null && videoID!=null && videoName !=null && imageurl!=null)
                            ((ListAdapterForLikes) lvVideo.getAdapter()).update(videoDetails);



                        } catch (JSONException e) {

                            e.printStackTrace();

                        }



                    }

                }, new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                    }

                });

                int socketTimeout = 30000;

                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                stringRequest.setRetryPolicy(policy);

                requestQueue.add(stringRequest);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                listAdapterForLikes.notifyDataSetChanged();
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


    void getHistory(){

        final DatabaseReference dataref1 = FirebaseDatabase.getInstance().getReference().child("User").child(MainActivity.user.getUid()).child("Like");
        dataref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ((ListAdapterForLikes) lvVideo.getAdapter()).updateHistory(dataSnapshot.getKey());
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
