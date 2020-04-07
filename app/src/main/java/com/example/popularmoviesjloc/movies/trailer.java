package com.example.popularmoviesjloc.movies;

public class trailer {
    //{"id":"5dad5e3afea6e30011ada0ab","iso_639_1":"en","iso_3166_1":"US","key":"F95Fk255I4M","name":"BLOODSHOT – International Trailer","site":"YouTube","size":1080,"type":"Trailer"}

    String id;
    String lenguage;
    String key;
    String name;
    int size;
    String site;

    public trailer(){

    }

    public void setID(String id){
        this.id=id;
    }

    public String getID(){
        return id;
    }

    public void setKey(String key){
        this.key=key;
    }

    public String getKey(){
        return key;
    }

    public void setLenguage(String lenguage){
        this.lenguage=lenguage;
    }

    public String getLenguage(){
        return lenguage;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setSite(String site){
        this.site=site;
    }

    public String getSite(){
        return site;
    }

    public void setSize(int size){
        this.size=size;
    }

    public int getSize(){
        return size;
    }

}
