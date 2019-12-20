package com.example.popularmoviesjloc;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.popularmoviesjloc.movies.movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.movieViewHolder> {
    ArrayList<movie> moviesList;
    Context contextMA;
    public movieAdapter(Context context){

            contextMA=context;
    }



    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context=parent.getContext();
        int contrasintid=R.layout.movie_poster;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(contrasintid,parent,false);



        movieViewHolder mVH=new movieViewHolder(view);

        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        movie movieSelected=moviesList.get(position);
        String pathPoster=movieSelected.getPosterPath();
        Picasso.with(contextMA).load("http://image.tmdb.org/t/p/w185/"+pathPoster).into(holder.posterMovie);
        Log.i("poster "+position,"http://image.tmdb.org/t/p/w185/"+pathPoster);


    }

    @Override
    public int getItemCount() {
        if(moviesList==null){
            return 0;
        }else{
            int size=moviesList.size();
            Log.i("getItemCount/Movies:",size+""); return moviesList.size();
        }


    }



    class movieViewHolder extends RecyclerView.ViewHolder{
        private ImageView posterMovie;

        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterMovie=(ImageView)itemView.findViewById(R.id.imageMovie);
        }
    }

    public void actualizandoData(ArrayList<movie> newMovies){
        moviesList=newMovies;
    }
}
