package com.example.popularmoviesjloc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.popularmoviesjloc.movies.movie;
import com.example.popularmoviesjloc.movies.review;
import com.example.popularmoviesjloc.movies.trailer;
import com.example.popularmoviesjloc.utilities.NetworkUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]>,trailersAdapter.onClickAdapter,reviewsAdapter.onClickAdapter {

    TextView durationMovie;
    Button markAsFavorite;
    final static int LOADER_ID=1988;
    String movieID;
    ArrayList<trailer> trailerList=new ArrayList();
    ArrayList<review> reviewList=new ArrayList();
    GridLayoutManager layoutManager;
    GridLayoutManager layoutManager_review;
    trailersAdapter trailerAdapter;
    reviewsAdapter reviewsAdapterObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent=getIntent();
        Log.i("DetailActivity","Starting");
        if(intent!=null){


            movie movieObjt = intent.getParcelableExtra("movie");

            try{


            TextView nameMovie = (TextView) findViewById(R.id.tv_movieName);
            //Log.i(this.getClass().getName(),movieObjt.getId());
                if(savedInstanceState!=null){
                    movieID=savedInstanceState.getString("movieID");

                }else{
                    movieID=movieObjt.getId();
                }

            nameMovie.setText(movieObjt.getTitle());

            ImageView poster = (ImageView) findViewById(R.id.tv_posterMovie);
            if(!movieObjt.getPosterPath().isEmpty()){
                Picasso.with(this).load(movieObjt.getPosterPath()).into(poster);
            }

            TextView yearRelease = (TextView) findViewById(R.id.tv_dateRelease);
            yearRelease.setText((movieObjt.getReleaseDate().toString()).substring(0,4));

            TextView rateMovie = (TextView) findViewById(R.id.tv_rateMovie);
            //rateMovie.setText(movieObjt.getVoteCount().concat(getString(R.string.rateValue)));
            rateMovie.setText(String.format(Double.toString(movieObjt.getVoteCount()),getString(R.string.rateValue)));
            TextView sinopsis = (TextView) findViewById(R.id.tv_sinopsisMovie);
            sinopsis.setText(movieObjt.getOverView());






            }catch (NullPointerException n){
                Log.e(this.getClass().getName(),n.getMessage());
            }


            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.trailersRecylerView);
            layoutManager=new GridLayoutManager(this,1);
            trailerAdapter=new trailersAdapter(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(trailerAdapter);

            RecyclerView review_recyclerView=(RecyclerView)findViewById(R.id.jloc_rv);
            reviewsAdapterObj=new reviewsAdapter(this);
            layoutManager_review=new GridLayoutManager(this,1);
            review_recyclerView.setLayoutManager(layoutManager_review);
            review_recyclerView.setAdapter(reviewsAdapterObj);

            LoaderManager.LoaderCallbacks callback=MovieDetail.this;

            getSupportLoaderManager().initLoader(LOADER_ID,null,callback);



        }






    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idMovie",movieID);
    }

    @NonNull
    @Override
    public Loader<String[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String[]>(this) {
            String[] trailers;
            String [] reviews;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
               Log.i(this.getClass().getName(),"onStartLoading");
                forceLoad();
            }

            @Nullable
            @Override
            public String[] loadInBackground() {
                Log.i(this.getClass().getName(),"loadInBackground");
                // https://api.themoviedb.org/3/
                String movie_db = getString(R.string.movie_db);
                String search="movie/";
                String get_movies_videos="/videos";
                String get_movies_reviews="/reviews";
                String lenguague_key="language";
                String lenguague_param="en-US";
                String api_key="api_key";
                String api_param=getString(R.string.apiKey);

                String [] api_keys={api_key,lenguague_key};
                String [] api_params={api_param,lenguague_param};
                // https://api.themoviedb.org/3/movie/
                String url_movie=movie_db.concat(search);
                // https://api.themoviedb.org/3/movie/{id}/
                String movie_search=url_movie.concat(movieID);
                // https://api.themoviedb.org/3/movie/{id}/videos
                String URLBase=movie_search.concat(get_movies_videos);
                Uri uri= NetworkUtils.getURI(URLBase,api_keys,api_params);
                URL url=NetworkUtils.getURL(uri);

                String URLBase_reviews=movie_search.concat(get_movies_reviews);
                Uri uri_reviews= NetworkUtils.getURI(URLBase_reviews,api_keys,api_params);
                URL url_review=NetworkUtils.getURL(uri_reviews);



                String result=null;
                String result_review=null;
                try {
                    result =NetworkUtils.getResponseFromHttpUrl(url);
                    result_review =NetworkUtils.getResponseFromHttpUrl(url_review);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return new String[]{result,result_review};
            }
        };
    }

    private   void stringToJson(String result){

        //Log.i(this.getClass().getName(),result);
        try{
            JSONObject results=new JSONObject(result);
            JSONArray trailers=results.getJSONArray("results");
            //Log.i("moviesLength:",trailers.length()+"");
            for(int i=0;i<=trailers.length();i++){
                if(!trailers.isNull(i)){
                    JSONObject trailersJSONObject=trailers.getJSONObject(i);
                    trailer trailerObj=new trailer();
                    try{
                        String id=trailersJSONObject.getString("id");
                        trailerObj.setID(id);
                        String name=trailersJSONObject.getString("name");
                        trailerObj.setName(name);
                        String size=trailersJSONObject.getString("size");
                        trailerObj.setSize(Integer.parseInt(size));
                        String site=trailersJSONObject.getString("site");
                        trailerObj.setSite(site);
                        String key=trailersJSONObject.getString("key");
                        trailerObj.setKey(key);

                        trailerList.add(trailerObj);



                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Log.i(this.getClass().getName(),trailerList.size()+"");




                }


            }




        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    private   void stringReviewToJsonReview(String result){

        //Log.i(this.getClass().getName(),result);
        try{
            JSONObject results=new JSONObject(result);
            JSONArray reviews=results.getJSONArray("results");
            //Log.i("moviesLength:",trailers.length()+"");
            for(int i=0;i<=reviews.length();i++){
                if(!reviews.isNull(i)){
                    JSONObject reviewsJSONObject=reviews.getJSONObject(i);
                    review reviewObj=new review();
                    try{
                        String author=reviewsJSONObject.getString("author");
                        reviewObj.setAuthor(author);
                        String content=reviewsJSONObject.getString("content");
                        reviewObj.setContent(content);
                        String url=reviewsJSONObject.getString("url");
                        reviewObj.setUrl(url);
                        String id=reviewsJSONObject.getString("id");
                        reviewObj.setId(id);


                        reviewList.add(reviewObj);



                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Log.i(this.getClass().getName(),trailerList.size()+"");




                }


            }




        }catch (JSONException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onLoadFinished(@NonNull Loader<String[]> loader, String[] data) {
        Log.i(this.getClass().getName(),data[0]);
        Log.i(this.getClass().getName(),data[1]);

        stringToJson(data[0]);
        stringReviewToJsonReview(data[1]);

        trailerAdapter.UpdateData(trailerList);
        reviewsAdapterObj.updateReviews(reviewList);

        trailerAdapter.notifyDataSetChanged();
        reviewsAdapterObj.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String[]> loader) {

    }

    @Override
    public void onClick(trailer trailerObj) {
        Log.i(this.getClass().getName(),"vnd.youtube:"+trailerObj.getKey());
        Uri youtube=Uri.parse("vnd.youtube:"+trailerObj.getKey());
        Intent intent=new Intent(Intent.ACTION_VIEW,youtube);
        startActivity(intent);

    }

    @Override
    public void onClickfromViewHolder(review reviewObj) {
        Log.i(this.getClass().getName(),reviewObj.getAuthor());
    }
}
