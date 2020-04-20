package com.example.popularmoviesjloc;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.movieEntry;
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
import java.util.List;


public class MainActivity extends AppCompatActivity implements movieAdapter.onClickAdapter {

    private movieAdapter mAdapter;


    //language=en-US

    String apiKeyParam;
    private final String sortQuery="sort_by";
    private static String sortParam=null;
    private  ArrayList<movie> movieList=new ArrayList<>();
    private  ArrayList<movie> local_movies=new ArrayList<>();
    private AppDatabase mDb;
    String name="jose luis";

    final private String apiKeyQuery="api_key";
    private final String[] keysUri={sortQuery,apiKeyQuery};
    private Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(this.getClass().getName(),"OnCreate:"+sortParam);
        if(savedInstanceState!=null){
            sortParam=savedInstanceState.getString("order");
            Log.i(this.getClass().getName(),"OnCreate:"+sortParam);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager=new GridLayoutManager(this,2);
        mAdapter=new movieAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        apiKeyParam = getString(R.string.apiKey);
        btnTryAgain=(Button)findViewById(R.id.btn_tryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonString();
            }
        });

        mDb=AppDatabase.getInstance(getApplicationContext());
        setupViewModel();
        getJsonString();



        //Log.i("url",url.toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(),"onRestart:"+sortParam);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(this.getClass().getName(),"onResume:"+name);

        Log.i(this.getClass().getName(),"onResume:"+sortParam);
        //setupViewModel();
        getJsonString();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getName(),"onStop:"+sortParam);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getName(),"onDestroy:"+sortParam);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("order",sortParam);
    }

    private  void getJsonString(){
        LinearLayout internetStat=(LinearLayout)findViewById(R.id.internetContainer);
        if(isONline()){
            if(apiKeyParam!=null&&!apiKeyParam.equals("")){
                movieList.clear();

                for(int i=0;i<local_movies.size();i++){
                    movieList.add(local_movies.get(i));

                }
                Log.i(this.getClass().getName(),"Added movies: "+movieList.size());
                internetStat.setVisibility(View.INVISIBLE);

                String URLBase = "https://api.themoviedb.org/3/discover/movie";
                Uri uri= NetworkUtils.getURI(URLBase,keysUri,new String[]{sortParam==null?"vote_average.asc":sortParam, apiKeyParam});
                URL url=NetworkUtils.getURL(uri);
                new movie_db(this).execute(url);
            }else{
                TextView txtView=(TextView)findViewById(R.id.tv_noInternetConnection);
                txtView.setText(getString(R.string.apiKey_invalid));
                btnTryAgain.setVisibility(View.INVISIBLE);
            }

        }else{
            internetStat.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movies_menu,menu);
//Esto es hecho con conexion remota

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.optionPopular){
            this.sortParam="popularity.desc";
        }else if(item.getItemId()==R.id.optionRate){
            this.sortParam="vote_average.dec";
        }
        Log.i(this.getClass().getName(),"Option::"+sortParam);
        //setupViewModel();
        getJsonString();
        return super.onOptionsItemSelected(item);
    }

    private   void stringToJson(String result){

//Log.i("stringToJson",result);
        try{
            JSONObject results=new JSONObject(result);
            JSONArray movies=results.getJSONArray("results");
  //          Log.i("moviesLength:",movies.length()+"");
            for(int i=0;i<=movies.length();i++){
                if(!movies.isNull(i)){
                    JSONObject movieFromDB=movies.getJSONObject(i);
                    movie movieX=new movie();
                    try{
                        String id=movieFromDB.getString("id");
                        movieX.setId(id);
                        String title=movieFromDB.getString("title");
                        movieX.setTitle(title);
                        String pathPoster=movieFromDB.getString("poster_path");
                        movieX.setPosterPath(pathPoster);
                        String popularity=movieFromDB.getString("popularity");
                        movieX.setPopularity(Double.parseDouble(popularity));
                        String vote_count=movieFromDB.getString("vote_average");
                        movieX.setVoteCount(Double.parseDouble(vote_count));
                        //String video=movieFromDB.getString("video");
                        //movieX.setVideo(Boolean.parseBoolean(video));
                        String overview=movieFromDB.getString("overview");
                        movieX.setOverView(overview);

                        String release_date=movieFromDB.optString ("release_date");
                        movieX.setReleaseDate(release_date);
                        if(!pathPoster.equals("null")){
                            boolean flag=false;
                            for(int j=0;j<local_movies.size();j++){
                                if(local_movies.get(j).getId().equals(movieX.getId())){
                                    flag=true;
                                }

                            }
                            if(!flag){
                                movieList.add(movieX);
                            }

                        }


                    }catch(JSONException e){
                        e.printStackTrace();
                    }





                }


            }

            //Log.i("stringToJson/movieList:",movieList.size()+"");


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
       // Log.i("onClick",":"+movieObj.getTitle());
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
           // Log.i("new list",s);
            activity.updateDataAdapter(activity.movieList);
        }
    }

    public boolean isONline(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        return netInfo!=null&&netInfo.isConnected();
    }

    public void setupViewModel(){
        MainViewModel mainViewModel= ViewModelProviders
                .of(this).get(MainViewModel.class);

        mainViewModel.getMovies().observe(this, new Observer<List<movieEntry>>() {
            @Override
            public void onChanged(List<movieEntry> movieEntries) {
                Log.i(this.getClass().getName(),"Updating liast of movies from LiveData in ViewModel");
                maping_movieEntries_movie(movieEntries);
                getJsonString();
            }
        });

        //AppDatabase.getInstance(this).trailersDAO().delete_Trailer();


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //mDb.trailersDAO().delete_Trailer();
                //mDb.movieDAO().deleteAllMovies();
            }
        });
    }

    public void maping_movieEntries_movie(List<movieEntry> movieEntries){
        local_movies.clear();
        Log.i(this.getClass().getName()+":276",""+movieEntries.size());
        for(int i=0;i<movieEntries.size();i++){
            movie movie_X=
            map_movieEntrie_movie(movieEntries.get(i));

            Log.i(this.getClass().getName()+":282",""+movieEntries.get(i).getIddB());

            local_movies.add(movie_X);

            //movieList.add(movie_X);

        }

        Log.i(this.getClass().getName(),"movieEntries maped:"+local_movies.size());
        getJsonString();

    }

    public movie map_movieEntrie_movie(movieEntry movieEntry){

        movie movieX=new movie();

        movieX.setId(movieEntry.getId());
        movieX.setTitle(movieEntry.getTitle());
        //movieX.setPopularity(movieEntry.getPopularity());
        movieX.setPosterPath(movieEntry.getPosterPath());
        Log.i(this.getClass().getName(),"PosterPath: "+movieX.getPosterPath());
            try{


                movieX.setOverView(movieEntry.getOverView());
                movieX.setReleaseDate(movieEntry.getReleaseDate());
                movieX.setVoteCount(movieEntry.getVoteCount());
                movieX.setTrailers(movieEntry.getTrailers());
                movieX.setReviews(movieEntry.getReviews());
                movieX.setLocal_id(movieEntry.getIddB());


            }catch (NullPointerException e){
                e.fillInStackTrace();
            }

            return movieX;
    }

    public void moviesFromDB(){


    }



}
