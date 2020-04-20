package com.example.popularmoviesjloc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.trailersEntry;

import java.util.List;

class AddTrailerViewModel  extends ViewModel {
    private LiveData<List<trailersEntry>> trailer;


    public AddTrailerViewModel(AppDatabase mDb, int mTrailerId) {
        trailer=mDb.trailersDAO().loadTrailerByMovieID(mTrailerId);
    }

    public LiveData<List<trailersEntry>> getTrailer(){
        return trailer;
    }
}
