package com.example.popularmoviesjloc.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface reviewDAO {

    @Query("SELECT * FROM review")
    LiveData<List<reviewEntry>> loadReviews();

    @Insert
    void inser_review(reviewEntry review);

    @Query("DELETE FROM review")
    void deleteAllReviews();

    @Query("SELECT * FROM review where movieDB=:id")
    LiveData<List<reviewEntry>> getReviewsByMovieID(int id);

    @Query("DELETE FROM review where id=:id")
    int deleteReviewByID(int id);


}
