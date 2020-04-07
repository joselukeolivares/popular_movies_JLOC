package com.example.popularmoviesjloc.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.popularmoviesjloc.movies.review;

import java.util.ArrayList;

@Entity(tableName = "movie")
public class movieEntry {
    @PrimaryKey(autoGenerate = true)
    private Double voteCount;
    private String posterPath;
    private String title;
    private String overView;
    private String releaseDate;
    private String id;
    private String trailers;

    public Double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    private String reviews;

    public movieEntry(Double voteCount, String posterPath, String title, String overView, String releaseDate, String id, String trailers, String reviews) {
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.title = title;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.id = id;
        this.trailers = trailers;
        this.reviews = reviews;
    }
}
