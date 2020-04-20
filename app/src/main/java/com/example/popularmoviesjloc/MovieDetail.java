package com.example.popularmoviesjloc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;

import com.example.popularmoviesjloc.DataBase.AppDatabase;
import com.example.popularmoviesjloc.DataBase.movieEntry;
import com.example.popularmoviesjloc.DataBase.reviewEntry;
import com.example.popularmoviesjloc.DataBase.trailersEntry;
import com.example.popularmoviesjloc.movies.movie;
import com.example.popularmoviesjloc.movies.review;
import com.example.popularmoviesjloc.movies.trailer;
import com.example.popularmoviesjloc.utilities.NetworkUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesjloc.utilities.NotificationBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]>,trailersAdapter.onClickAdapter,reviewsAdapter.onClickAdapter {


    TextView durationMovie;
    Button markAsFavorite;
    final static int LOADER_ID=1988;
    ImageView favoriteIcon;
    String movieID;
    ArrayList<trailer> trailerList=new ArrayList();
    ArrayList<reviewEntry> reviewList=new ArrayList();
    GridLayoutManager layoutManager;
    GridLayoutManager layoutManager_review;
    trailersAdapter trailerAdapter;
    reviewsAdapter reviewsAdapterObj;
    private boolean favorite_movie=false;
    private AppDatabase mDb;
    movie movieObjt;
    private int id_movie_entry;
    boolean findMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Log.i(this.getClass().getName(),"onCreate");




        Intent intent=getIntent();
        Log.i("DetailActivity","Starting");
        mDb=AppDatabase.getInstance(this.getApplicationContext());

        AddMovieViewModelFactory factory;
        final AddMovieViewModel viewModel;
        favoriteIcon=(ImageView)findViewById(R.id.favorite_icon);

        if(intent!=null){


            movieObjt = intent.getParcelableExtra("movie");

            movieID=movieObjt.getId();
            Log.i(this.getClass().getName(),"Intent!=null & ID: "+movieID);

            factory=new AddMovieViewModelFactory(mDb,movieObjt.getLocal_id());
            viewModel= ViewModelProviders.of(this,factory).get(AddMovieViewModel.class);

            viewModel.getMovie().observe(this, new Observer<movieEntry>() {
                @Override
                public void onChanged(movieEntry movieEntry) {
                    if(movieEntry!=null){
                        viewModel.getMovie().removeObserver(this);
                        Log.i(this.getClass().getName(),movieEntry.getTitle());
                    }


                }
            });



            //try{


            TextView nameMovie = (TextView) findViewById(R.id.tv_movieName);
            markAsFavorite=(Button)findViewById(R.id.button_favoriteMark);
            markAsFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    final boolean flag=true;
                    Log.i(this.getClass().getName(),"Marking Favorite");
                    //if movieObjt.getLocal_id()>0 movie is in DB

                    if(favorite_movie){
                        //Delete movie from DB, with foreign key the trailers must be deleted

                        favoriteIcon.setVisibility(View.INVISIBLE);
                        markAsFavorite.setText("MARK AS FAVORITE");

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.movieDAO().deleteMovieById(movieObjt.getLocal_id());
                                movieObjt.setLocal_id(0);
                                favorite_movie=false;
                                notificationBuilder(false,movieObjt.getTitle());
                            }


                        });

                        update_movie_status(false);

                    }else{
                        //Save movie and trailers in DB
                         searchMovie(movieObjt.getLocal_id());

                        if(findMovie){
                            Toast toast=Toast.makeText(getApplicationContext(),"The movie already exist like favorite",Toast.LENGTH_LONG);
                            toast.show();
                        }else {


                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    movieEntry movie_entry = map_movie_movieEntry(movieObjt);
                                    id_movie_entry = (int) mDb.movieDAO().insertMovie(movie_entry);

                                    if (id_movie_entry > 0) {

                                        notificationBuilder(true, movieObjt.getTitle());
                                        movieObjt.setLocal_id(id_movie_entry);

                                        favorite_movie = true;
                                        insertReviews(id_movie_entry);
                                    }
                                    Log.i(this.getClass().getName(), "Imagin: Inserting movie");
                                    Log.i(this.getClass().getName(), "poster: " + movieObjt.getPosterPath());


                                }

                            });

                            favorite_movie=true;
                            update_movie_status(true);
                            /*
                            Movie inserted
                             */
                            //if(id_movie_entry>0){

                            //}

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < trailerList.size(); i++) {
                                        trailer trailerObj = trailerList.get(i);

                                        String id = trailerObj.getID() != null ? trailerObj.getID() : "0";
                                        String key = trailerObj.getKey() != null ? trailerObj.getKey() : "0";
                                        String name = trailerObj.getName() != null ? trailerObj.getName() : "0";
                                        String size = trailerObj.getSite() != null ? trailerObj.getSite() : "0";
                                        String id_movieObj = movieObjt.getId() != null ? movieObjt.getId() : "0";
                                        int id_local = movieObjt.getLocal_id();

                                        Log.i(this.getClass().getName() + ":inserting Trailer", "" + key);
                                        mDb.trailersDAO().insert_Trailer(new trailersEntry(id_movie_entry, key, name, size, id_movieObj));
                                        Log.i(this.getClass().getName(), "Trialer inserted");
                                    }
                                }
                            });





                        }

                    }

                }
            });

                //Log.i(this.getClass().getName()+"161",savedInstanceState.get("movieID")+"");

                if(savedInstanceState!=null){
                    //movieID=savedInstanceState.getString("movieID");
                    Log.i(this.getClass().getName()+"168",movieObjt.getId());

                }else{
                    Log.i(this.getClass().getName()+"172",movieObjt.getId());
                    //movieID=movieObjt.getId();

                }

            nameMovie.setText(movieObjt.getTitle());

            ImageView poster = (ImageView) findViewById(R.id.tv_posterMovie);
            if(!movieObjt.getPosterPath().isEmpty()){
                Picasso.with(this).load(movieObjt.getPosterPath()).into(poster);
            }

            TextView yearRelease = (TextView) findViewById(R.id.tv_dateRelease);
            String yearDate=movieObjt.getReleaseDate().toString();
            if(yearDate.length()>0){
                yearRelease.setText((yearDate).substring(0,4));
            }


            TextView rateMovie = (TextView) findViewById(R.id.tv_rateMovie);
            //rateMovie.setText(movieObjt.getVoteCount().concat(getString(R.string.rateValue)));
            rateMovie.setText(String.format(Double.toString(movieObjt.getVoteCount()),getString(R.string.rateValue)));
            TextView sinopsis = (TextView) findViewById(R.id.tv_sinopsisMovie);
            sinopsis.setText(movieObjt.getOverView());






            //}catch (NullPointerException n){
              //  Log.e(this.getClass().getName(),n.getMessage());
            //}


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

            if(movieObjt.getLocal_id()>0){
                favorite_movie=true;
                markAsFavorite.setText("DISMARK AS FAVORITE ");
                get_Trailers_Reviews_DB(movieObjt.getLocal_id());
                getReviewsFromDB(movieObjt.getLocal_id());
                favoriteIcon.setVisibility(View.VISIBLE);


            }else{
                favorite_movie=false;
                getSupportLoaderManager().initLoader(LOADER_ID,null,callback);
            }






        }



    }


    public void populateUI(){

    }

    public void notificationBuilder(boolean addMovie,String title){


                NotificationBuilder.movie_modified_db(this,addMovie,title);



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(this.getClass().getName(),"onSaveInstanceState");
        outState.putString("idMovie",movieID);


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getName(),"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(),"onResume");


    }

    @NonNull
    @Override
    public Loader<String[]> onCreateLoader(int id,final  @Nullable Bundle args) {


        return new AsyncTaskLoader<String[]>(this) {
            String[] trailers;
            String [] reviews;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
               Log.i(this.getClass().getName(),"onStartLoading"+trailerList.size());
                if(trailerList.size()<1){
                    forceLoad();
                    Log.i(this.getClass().getName(),"onStartLoading: forceLoad");
                }else{
                    Log.i(this.getClass().getName(),"onStartLoading: NOT forceLoad");
                }


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
                    reviewEntry reviewObj;
                    try{
                        String author=reviewsJSONObject.getString("author");

                        String content=reviewsJSONObject.getString("content");

                        String url=reviewsJSONObject.getString("url");

                        String id=reviewsJSONObject.getString("id");

                        reviewObj=new reviewEntry(author,content);
                        reviewObj.setUrl(url);
                        reviewObj.setId_api(id);


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
        //Log.i(this.getClass().getName(),data[0]);
        //Log.i(this.getClass().getName(),data[1]);

        stringToJson(data[0]);
        stringReviewToJsonReview(data[1]);

        updateTrailers_Reviews_favorites();

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
    public void onClickfromViewHolder(reviewEntry reviewObj) {
        Log.i(this.getClass().getName(),reviewObj.getAuthor());
    }

    public movieEntry map_movie_movieEntry(movie movieX){
        movieEntry movie_entryX=new movieEntry();
        movie_entryX.setId(movieX.getId());
        movie_entryX.setOverView(movieX.getOverView());
        movie_entryX.setPosterPath(movieX.getPosterCode());
        Log.i(this.getClass().getName(),movie_entryX.getPosterPath());
        Log.i(this.getClass().getName(),movieX.getPosterPath());
        //movie_entryX.setReviews(movieX.getReviews());
        //movie_entryX.setTrailers(movieX.getTrailers());
        movie_entryX.setTitle(movieX.getTitle());
        movie_entryX.setReleaseDate(movieX.getReleaseDate());
        movie_entryX.setVoteCount(movieX.getVoteCount());
        return movie_entryX;
    }

    public void updateTrailers_Reviews_favorites(){
        trailerAdapter.UpdateData(trailerList);
        reviewsAdapterObj.updateReviews(reviewList);

        trailerAdapter.notifyDataSetChanged();
        reviewsAdapterObj.notifyDataSetChanged();
    }

    public void get_Trailers_Reviews_DB(int idMovieDB){
        /*


         */

        AddTrailerViewModel trailerViewModel=new AddTrailerViewModel(mDb,idMovieDB);

        trailerViewModel.getTrailer().observe(this, new Observer<List<trailersEntry>>() {
            @Override
            public void onChanged(List<trailersEntry> trailersEntries) {
                Log.i(this.getClass().getName(),"Updating trailers");
                Log.i(this.getClass().getName(),"Trailers: "+trailersEntries.size());
                mapinp_trailerEntries_trailer(trailersEntries);
            }
        });


    }

    public void mapinp_trailerEntries_trailer(List<trailersEntry> trailersEntries){
        Log.i(this.getClass().getName(),"mapinp_trailerEntries_trailer");
        for (int i=0;i<trailersEntries.size();i++){
            trailersEntry trailerEntryObj=trailersEntries.get(i);
            trailer trailerObj=new trailer();
            trailerObj.setID(trailerEntryObj.getId_original());
            trailerObj.setName(trailerEntryObj.getName());
            trailerObj.setSize((trailerEntryObj.getSize()));
            trailerObj.setLenguage((trailerEntryObj.getLenguage()));
            trailerObj.setSite(trailerEntryObj.getSite());
            trailerObj.setKey(trailerEntryObj.getKey());

            trailerList.add(trailerObj);

        }

        updateTrailers_Reviews_favorites();
    }

    public void searchMovie(int id){

        Log.i(this.getClass().getName(),"Searching");
        final movieEntry movieEntry_out;

        AddMovieViewModel addMovieViewModel=ViewModelProviders.of(this).get(AddMovieViewModel.class);
        addMovieViewModel.getMovie().observe(this, new Observer<movieEntry>() {

            @Override
            public void onChanged(movieEntry movieEntry) {
                if(movieEntry!=null){
                    Log.i(this.getClass().getName(),"The movie exist"+movieEntry.getTitle());
                    findMovie=true;
                    //movieEntry_out=movieEntry;
                }else{
                    Log.i(this.getClass().getName(),"The movie Not exist");
                    findMovie=false;

                }
            }

        });

    }

    public  void update_movie_status(boolean favorite_movie){

        if(favorite_movie){
            favoriteIcon.setVisibility(View.VISIBLE);
            markAsFavorite.setText("DISMARK AS FAVORITE");
        }else{
            favoriteIcon.setVisibility(View.INVISIBLE);
            markAsFavorite.setText("MARK AS FAVORITE");
        }
    }

    public void getReviewsFromDB(int movieID){


            AddReviewViewModel addReviewViewModel=new AddReviewViewModel(mDb,movieID);

            addReviewViewModel.getReviews().observe(this, new Observer<List<reviewEntry>>() {
                @Override
                public void onChanged(List<reviewEntry> reviewEntries) {
                  Log.i(this.getClass().getName(),"Reviews from DB: "+reviewEntries.size());
                    for(int i=0;i<reviewEntries.size();i++){
                      reviewList.add(reviewEntries.get(i));
                  }
                    reviewsAdapterObj.updateReviews(reviewList);
                }
            });


    }

    public void insertReviews(final int movieID){
        Log.i(this.getClass().getName(),"Inserting reviews "+movieID);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<reviewList.size();i++){
                    reviewEntry reviewEntryObj=reviewList.get(i);
                    reviewEntryObj.setMovieDB(movieID);
                    mDb.reviewDAO().inser_review(reviewEntryObj);
                }
            }
        });
    }



}
