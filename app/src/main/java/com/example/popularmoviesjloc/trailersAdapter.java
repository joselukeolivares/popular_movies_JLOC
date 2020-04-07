package com.example.popularmoviesjloc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesjloc.movies.trailer;

import java.util.ArrayList;

public class trailersAdapter extends RecyclerView.Adapter<trailersAdapter.trailerViewHolder> {
    private ArrayList<trailer> trailerList=new ArrayList<>();
    Context context;
    onClickAdapter myOnclickAdapter;

    //private final onClickAdapater onClickAdapaterview;
    public trailersAdapter(onClickAdapter onClickAdapter){
        myOnclickAdapter=onClickAdapter;


    }

    public interface onClickAdapter{
        public void onClick(trailer trailerObj);
    }

    @NonNull
    @Override
    public trailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int constraintID=R.layout.trailers_movie;

        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(constraintID,parent,false);
        return new trailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull trailerViewHolder holder, int position) {
    trailer trailer=trailerList.get(position);

    String name=trailer.getName();
    String trailerTXT="Trailer "+trailer.getName();
    holder.textView.setText(trailerTXT.concat(Integer.toString(position)));

    }

    @Override
    public int getItemCount() {
        if(trailerList!=null){
             Log.i(this.getClass().getName(),"DATA Size:"+trailerList.size());
             return trailerList.size();

        }else{
            Log.i(this.getClass().getName(),"DATA is Null"+trailerList.size());
            return 0;
        }

    }

    public class trailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;

        public trailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            trailer trailerOBJ=trailerList.get(getAdapterPosition());
            myOnclickAdapter.onClick(trailerOBJ);
        }
    }

    public void UpdateData(ArrayList<trailer> newData){
        Log.i(this.getClass().getName(),"DataUpdated:"+newData.size());
        trailerList=newData;
    }


}
