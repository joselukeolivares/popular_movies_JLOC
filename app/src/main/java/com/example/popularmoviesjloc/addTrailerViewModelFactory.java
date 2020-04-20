package com.example.popularmoviesjloc;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmoviesjloc.DataBase.AppDatabase;

public class addTrailerViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mMovieId;



    public addTrailerViewModelFactory(AppDatabase mDb, int mMovieId){
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    public <T extends ViewModel> T  create(Class<T> modelClass){
        return (T) new AddTrailerViewModel(mDb,mMovieId);
    }

}
