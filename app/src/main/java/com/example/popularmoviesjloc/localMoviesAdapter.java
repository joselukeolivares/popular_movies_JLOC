package com.example.popularmoviesjloc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesjloc.DataBase.movieEntry;

import java.util.List;

public class localMoviesAdapter extends RecyclerView.Adapter<localMoviesAdapter.localMoviesVH> {
    private List<movieEntry> movieEntriesList;
    private Context context;
    private final OnClickAdapter onClickAdapter;

    public interface OnClickAdapter{
            void onClick(movieEntry movie);
    }

    public localMoviesAdapter(OnClickAdapter onClickAdapter) {
        this.onClickAdapter = onClickAdapter;
    }

    @NonNull
    @Override
    public localMoviesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull localMoviesVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class localMoviesVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        public localMoviesVH(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
