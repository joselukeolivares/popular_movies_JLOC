package com.example.popularmoviesjloc.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface movieDAO {

    @Query("SELECT * FROM movie")
    LiveData<List<movieEntry>> loadAllMovies();

    @Insert
    void insertMovie(movieEntry movieEntry);

    @Delete
    void deleteMovie(movieEntry movieEntry);

    @Query("SELECT * FROM movie WHERE id=:id")
    LiveData<movieEntry> loadMovieByID(int id);
}
