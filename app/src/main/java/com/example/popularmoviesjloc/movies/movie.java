package com.example.popularmoviesjloc.movies;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public class movie implements Parcelable  {
    double popularity;
    double voteCount;
    boolean video;
    String posterPath;
    int id;
    boolean adult;
    String backdropPath;
    String originalLenguage;
    String OriginalTitle;
    ArrayList<Integer> genreIDs;
    String title;
    double vote_average;
    String overView;
    String releaseDate;
    private static final String DATE_OUTPUT_FORMAT="yyyy-MM-dd";
    public movie(){

    }

    public movie(Parcel in){
        readFromParcel(in);
    }


   public static final Parcelable.Creator<movie> CREATOR= new Parcelable.Creator<movie>(){
        public movie createFromParcel(Parcel in){
            return new movie(in);
        }

       @Override
       public movie[] newArray(int i) {
           return new movie[i];
       }
   };



    public void setPopularity(double popularityParam){
        popularity=popularityParam;
    }
    public double getPopularity(){
        return popularity;
    }

    public void setVoteCount(double voteCountParam){
        voteCount=voteCountParam;
    }
    public double getVoteCount(){
        return voteCount;
    }

    public void setVideo(boolean videoParam){
        video=videoParam;
    }
    public boolean getVideo(){
        return video;
    }

    public void setTitle(String titleParam){
        title=titleParam;
    }
    public String getTitle(){
        return title;
    }

    public void setOverView(String overViewParam){
        overView=overViewParam;
    }
    public String getOverView(){
        return overView;
    }

    public void setReleaseDate(String ReleaseDateParam)  {
        releaseDate=ReleaseDateParam;




    }
    public String getReleaseDate(){
        return releaseDate;
    }

    public void setPosterPath(String posterPathParam){
        posterPath="http://image.tmdb.org/t/p/w185/"+posterPathParam;
    }
    public String getPosterPath(){
        return posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.releaseDate);
        parcel.writeDouble(this.voteCount);
        parcel.writeString(this.overView);
    }

    public void readFromParcel(Parcel in){
        title=in.readString();
        posterPath=in.readString();
        releaseDate=in.readString();
        voteCount=in.readDouble();
        overView=in.readString();
    }
}
