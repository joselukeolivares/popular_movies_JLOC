package com.example.popularmoviesjloc.DataBase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "review",foreignKeys =@ForeignKey(
        entity = movieEntry.class,
        parentColumns = "iddB",
        childColumns="movieDB",
        onDelete = CASCADE
))
public class reviewEntry {
    @PrimaryKey(autoGenerate = true)
    int id;

    public int getMovieDB() {
        return movieDB;
    }

    public void setMovieDB(int movieDB) {
        this.movieDB = movieDB;
    }

    int movieDB;
    String author;
    String content;
    String id_api;
    String url;

    public reviewEntry(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_api() {
        return id_api;
    }

    public void setId_api(String id_api) {
        this.id_api = id_api;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
