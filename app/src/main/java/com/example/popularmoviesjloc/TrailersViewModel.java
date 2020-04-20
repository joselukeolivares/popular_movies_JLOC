package com.example.popularmoviesjloc;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.trailersEntry;

import java.util.List;

public class TrailersViewModel extends AndroidViewModel {
    LiveData<List<trailersEntry>> trailers;

    public TrailersViewModel(@NonNull Application application) {
        super(application);

        AppDatabase mDb=AppDatabase.getInstance(this.getApplication());
        this.trailers=mDb.trailersDAO().loadTrailers();
    }

    public LiveData<List<trailersEntry>> getTrailers(){
        return trailers;
    }
}
