package com.example.popularmoviesjloc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.reviewEntry;

import java.util.List;

public class AddReviewViewModel extends ViewModel {
     private LiveData<List<reviewEntry>> reviews;

     public  AddReviewViewModel(AppDatabase mDB,int movieID){
         reviews=mDB.reviewDAO().getReviewsByMovieID(movieID);
     }

     public LiveData<List<reviewEntry>> getReviews(){
         return reviews;
     }
}
