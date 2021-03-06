package com.example.popularmoviesjloc.DataBase;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {movieEntry.class,trailersEntry.class,reviewEntry.class},version = 5, exportSchema = false)

public abstract class AppDatabase  extends RoomDatabase {

    private static final String LOG_TAG=AppDatabase.class.getName();
    private  static final Object LOCK=new Object();
    private static  final String DATABASE_NAME="movieBase";
    private static  AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.i(LOG_TAG,"Creating new database instance");
                sInstance= Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME
                ).fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.i(AppDatabase.LOG_TAG,"Getting the database instance");
        return sInstance;
    }

    public abstract movieDAO movieDAO();

    public abstract trailersDAO trailersDAO();

    public abstract reviewDAO reviewDAO();

}
