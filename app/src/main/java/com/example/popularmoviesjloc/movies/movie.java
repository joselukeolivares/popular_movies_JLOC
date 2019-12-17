package com.example.popularmoviesjloc.movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class movie {
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


}
