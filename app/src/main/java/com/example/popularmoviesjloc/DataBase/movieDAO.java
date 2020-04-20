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
    long insertMovie(movieEntry movieEntry);

    @Delete
    void deleteMovie(movieEntry movieEntry);

    @Query("DELETE FROM movie")
    void deleteAllMovies();

    @Query("DELETE FROM movie where idDB=:id")
    void deleteMovieById(int id);

    @Query("SELECT * FROM movie WHERE id=:id")
    LiveData<movieEntry> loadMovieByID(int id);
}

/*

Estoy haciendo mi declaración anual, y hay munto como deducible y me gustaría ver
si con las facturas de la renta del consultorio puedo recuperar algo de ello, en que clasificación
corresponde

 */