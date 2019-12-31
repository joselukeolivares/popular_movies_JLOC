package com.example.popularmoviesjloc;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.popularmoviesjloc.movies.movie;
import com.example.popularmoviesjloc.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements movieAdapter.onClickAdapter {

    private movieAdapter mAdapter;


    //language=en-US


    private final String sortQuery="sort_by";
    private String sortParam="vote_average.desc";
    private  ArrayList<movie> movieList;

    final private String apiKeyQuery="api_key";
    private final String[] keysUri={sortQuery,apiKeyQuery};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager=new GridLayoutManager(this,2);
        mAdapter=new movieAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        getJsonString();








        //Log.i("url",url.toString());
    }

    private  void getJsonString(){
        movieList=new ArrayList<>();
        String apiKeyParam = "b70d678e90b7b2248b8795db25cd8d26";
        String URLBase = "https://api.themoviedb.org/3/discover/movie";
        Uri uri= NetworkUtils.getURI(URLBase,keysUri,new String[]{sortParam, apiKeyParam});
        URL url=NetworkUtils.getURL(uri);
        new movie_db(this).execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movies_menu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionPopular:
                sortParam="popularity.desc";
                getJsonString();
            case R.id.optionRate:
                sortParam="vote_average.asc";
                getJsonString();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private   void stringToJson(String result){

Log.i("stringToJson",result);
        try{
            JSONObject results=new JSONObject(result);
            JSONArray movies=results.getJSONArray("results");
            Log.i("moviesLength:",movies.length()+"");
            for(int i=0;i<=movies.length();i++){
                if(!movies.isNull(i)){
                    JSONObject movieFromDB=movies.getJSONObject(i);
                    movie movieX=new movie();
                    try{
                        String title=movieFromDB.getString("title");
                        movieX.setTitle(title);
                        String pathPoster=movieFromDB.getString("poster_path");
                        movieX.setPosterPath(pathPoster);
                        String popularity=movieFromDB.getString("popularity");
                        movieX.setPopularity(Double.parseDouble(popularity));
                        String vote_count=movieFromDB.getString("vote_average");
                        movieX.setVoteCount(Double.parseDouble(vote_count));
                        String video=movieFromDB.getString("video");
                        movieX.setVideo(Boolean.parseBoolean(video));
                        String overview=movieFromDB.getString("overview");
                        movieX.setOverView(overview);

                        String release_date=movieFromDB.optString ("release_date");
                        movieX.setReleaseDate(release_date);
                        if(!pathPoster.equals("null")){
                            movieList.add(movieX);
                        }


                    }catch(JSONException e){
                        e.printStackTrace();
                    }





                }


            }

            Log.i("stringToJson/movieList:",movieList.size()+"");


        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    private   void updateDataAdapter(ArrayList<movie> itemList){
        mAdapter.actualizandoData(itemList);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(movie movieObj) {
        Log.i("onClick",":"+movieObj.getTitle());
        Intent intent=new Intent(this.getApplicationContext(), MovieDetail.class);

        intent.putExtra("movie",movieObj);
        startActivity(intent);
    }

    static class movie_db extends AsyncTask<URL,Integer,String> {

        private final WeakReference<MainActivity> reference;
        movie_db(MainActivity context){
            reference=new WeakReference<>(context);
        }
        @Override
        protected String doInBackground(URL... urls) {
            String result=null;
            try{
                result= NetworkUtils.getResponseFromHttpUrl(urls[0]);
            }catch(IOException e){
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity activity=reference.get();
            activity.stringToJson(s);
            Log.i("new list",s);
            activity.updateDataAdapter(activity.movieList);
        }
    }



}
