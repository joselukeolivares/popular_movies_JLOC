package com.example.popularmoviesjloc;

import android.content.Context;
import android.content.Intent;
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
    private ArrayList<movie> moviesList;
    private Context context;
    private final onClickAdapter onClickImgView;

    public movieAdapter( onClickAdapter onClickMovie){
        onClickImgView=onClickMovie;


    }

    public interface onClickAdapter{
        void onClick(movie movieObj);
    }



    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        int contrasintid=R.layout.movie_poster;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(contrasintid,parent,false);





        return new movieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        movie movieSelected=moviesList.get(position);
        String pathPoster=movieSelected.getPosterPath();
        holder.indexList=position;
        Picasso.with(context).load(pathPoster).into(holder.posterMovie);
        //Log.i("poster "+position,"http://image.tmdb.org/t/p/w185/"+pathPoster);
          Log.i("poster "+position,""+movieSelected.getPosterPath());
          //Movie is favorite, marking the star
          if(movieSelected.getLocal_id()>0){
              holder.yellow_star.setVisibility(View.VISIBLE);
          }


    }

    @Override
    public int getItemCount() {
        if(moviesList==null){
            return 0;
        }else{
            @SuppressWarnings("unused") int size=moviesList.size();
            //Log.i("getItemCount/Movies:",size+"");
            return moviesList.size();
        }


    }



    class movieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView posterMovie;
        private final ImageView yellow_star;
        private int indexList;

        movieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterMovie=(ImageView)itemView.findViewById(R.id.imageMovie);
            yellow_star=(ImageView)itemView.findViewById(R.id.yellow_star);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            movie movieObjSelected=moviesList.get(getAdapterPosition());
            onClickImgView.onClick(movieObjSelected);



        }
    }

    public void actualizandoData(ArrayList<movie> newMovies){
        String title=newMovies.get(0).getTitle();
        Log.i("actualizandoData","NewMovies:"+title);
        this.moviesList=newMovies;
    }
}
