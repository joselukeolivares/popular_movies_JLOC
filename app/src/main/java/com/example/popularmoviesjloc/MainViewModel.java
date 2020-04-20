package com.example.popularmoviesjloc;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.movieEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<movieEntry>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getInstance(this.getApplication());
        movies=database.movieDAO().loadAllMovies();

    }

    public LiveData<List<movieEntry>> getMovies(){return  movies;}
}
