package com.example.popularmoviesjloc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.movieEntry;

class AddMovieViewModel extends ViewModel {

     private LiveData<movieEntry> movie;

    public AddMovieViewModel(AppDatabase mDb, int mMovieID) {
        movie=mDb.movieDAO().loadMovieByID(mMovieID);
    }

    public LiveData<movieEntry> getMovie(){return movie;}
}
