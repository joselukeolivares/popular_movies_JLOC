package com.example.popularmoviesjloc;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmoviesjloc.DataBase.AppDatabase;

import java.util.concurrent.Executor;

public class AddMovieViewModelFactory  extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final  int mMovieID;
    //private final Executor executor;


    public AddMovieViewModelFactory(AppDatabase mDb, int mMovieID) {
        this.mDb = mDb;
        this.mMovieID = mMovieID;
        //this.executor = executor1;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddMovieViewModel(mDb,mMovieID);
    }


}
