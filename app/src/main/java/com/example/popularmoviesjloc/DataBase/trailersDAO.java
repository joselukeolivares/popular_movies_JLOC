package com.example.popularmoviesjloc.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmoviesjloc.movies.trailer;

import java.util.List;

@Dao
public interface trailersDAO {

    @Query("SELECT * FROM trailer")
    LiveData<List<trailersEntry>> loadTrailers();

    @Insert
    void insert_Trailer(trailersEntry trailer);

    @Query("DELETE  FROM trailer")
    void delete_Trailer();

    @Query("SELECT * FROM trailer WHERE idDB=:idDB")
    LiveData<List<trailersEntry>> loadTrailerByMovieID(int idDB);


}
