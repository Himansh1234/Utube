package com.example.youtubex;
public class VideoDetails {

    String VideoName;

    String VideoDesc;

    String URL;

    String VideoId;


    String viewsCount;
    String likesCount;



    public void setVideoName(String VideoName){

        this.VideoName=VideoName;

    }


    public void setViewsCount(String x){

        this.viewsCount=x;

    }


    public void setLikesCount(String x){

        this.likesCount=x;

    }

    public String getVideoName(){

        return VideoName;

    }



    public void setVideoDesc(String VideoDesc){

        this.VideoDesc=VideoDesc;

    }



    public String getVideoDesc(){

        return VideoDesc;

    }



    public void setURL(String URL){

        this.URL=URL;

    }



    public String getURL(){

        return URL;

    }



    public void setVideoId(String VideoId){

        this.VideoId=VideoId;

    }

    public String getVideoId(){

        return VideoId;

    }

    public String getViewsCount(){

        return viewsCount;

    }

    public String getLikesCount(){

        return likesCount;

    }

}
