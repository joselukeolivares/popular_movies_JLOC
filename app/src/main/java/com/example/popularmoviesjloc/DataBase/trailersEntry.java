package com.example.popularmoviesjloc.DataBase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "trailer",foreignKeys =@ForeignKey(entity = movieEntry.class,
        parentColumns = "iddB",
        childColumns = "idDB",
        onDelete = CASCADE))
public class trailersEntry {

    @PrimaryKey (autoGenerate = true)
    int id;
    int idDB;
    String lenguage;
    String key;
    String name;
    int size;
    String site;
    private String movieID;

    public String getId_original() {
        return id_original;
    }

    public void setId_original(String id_original) {
        this.id_original = id_original;
    }

    String id_original;

    public trailersEntry(int idDB, String key, String name, String site,String movieID) {
        this.idDB = idDB;
        this.key = key;
        this.name = name;
        this.site = site;
        this.movieID=movieID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }

    public String getLenguage() {
        return lenguage;
    }

    public void setLenguage(String lenguage) {
        this.lenguage = lenguage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }


}
