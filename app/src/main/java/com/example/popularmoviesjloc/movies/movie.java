package com.example.popularmoviesjloc.movies;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class movie implements Parcelable {
    double popularity;
    int voteCount;
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
    Date releaseDate;
    private static final String DATE_OUTPUT_FORMAT="yyyy-MM-dd";
    public movie(){

    }

    protected movie(Parcel in) {
        popularity = in.readDouble();
        voteCount = in.readInt();
        video = in.readByte() != 0;
        posterPath = in.readString();
        id = in.readInt();
        adult = in.readByte() != 0;
        backdropPath = in.readString();
        originalLenguage = in.readString();
        OriginalTitle = in.readString();
        title = in.readString();
        vote_average = in.readDouble();
        overView = in.readString();
    }

    public static final Creator<movie> CREATOR = new Creator<movie>() {
        @Override
        public movie createFromParcel(Parcel in) {
            return new movie(in);
        }

        @Override
        public movie[] newArray(int size) {
            return new movie[size];
        }
    };

    public void setPopularity(double popularityParam){
        popularity=popularityParam;
    }
    public double getPopularity(){
        return popularity;
    }

    public void setVoteCount(int voteCountParam){
        voteCount=voteCount;
    }
    public int getVoteCount(){
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

    public void setReleaseDate(String ReleaseDateParam) throws ParseException {
        SimpleDateFormat dataFormtat=new SimpleDateFormat("yyyy-mm-dd");

        releaseDate=dataFormtat.parse(ReleaseDateParam);
    }
    public Date getReleaseDate(){
        return releaseDate;
    }

    public void setPosterPath(String posterPathParam){
        posterPath=posterPathParam;
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
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeString(posterPath);
        parcel.writeInt(id);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(backdropPath);
        parcel.writeString(originalLenguage);
        parcel.writeString(OriginalTitle);
        parcel.writeString(title);
        parcel.writeDouble(vote_average);
        parcel.writeString(overView);
    }
}
