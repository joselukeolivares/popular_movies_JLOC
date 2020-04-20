package com.example.popularmoviesjloc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesjloc.DataBase.reviewEntry;
import com.example.popularmoviesjloc.movies.review;
import com.example.popularmoviesjloc.movies.trailer;

import java.util.ArrayList;

public class reviewsAdapter extends RecyclerView.Adapter<reviewsAdapter.reviewVH> {
    Context context;
    ArrayList<reviewEntry> reviewsList =new ArrayList<>();
    onClickAdapter onClickAdapterObj;
    public reviewsAdapter(onClickAdapter onClickAdapterObj) {
        this.onClickAdapterObj=onClickAdapterObj;

        review reviewObj=new review();
        reviewObj.setAuthor("joseluisoc");
        reviewObj.setContent("joseluisoc");
        reviewObj.setUrl("joseluisoc");

        /*
        for(int i=0;i<100;i++){
            reviewsList.add(reviewObj);
        }
        */
        Log.i(this.getClass().getName(),reviewsList.size()+"");


    }

    public interface onClickAdapter{
        public void onClickfromViewHolder(reviewEntry reviewObj);
    }

    @NonNull
    @Override
    public reviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout=R.layout.review;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);


        return new reviewVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewVH holder, int position) {
        reviewEntry reviewObj= reviewsList.get(position);
        String author=reviewObj.getAuthor();
        String content=reviewObj.getContent();
        String url=reviewObj.getUrl();

        holder.author.setText(author);
        holder.content.setText(content);
        holder.url.setText(url+position);



    }

    @Override
    public int getItemCount() {
        if(reviewsList!=null){
            return reviewsList.size();
        }else{
            return 0;
        }

    }

    public class reviewVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author;
        TextView content;
        TextView url;
        public reviewVH(@NonNull View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.Author_text);
            content=itemView.findViewById(R.id.content_text);
            url=itemView.findViewById(R.id.url_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            reviewEntry reviewObj=reviewsList.get(getAdapterPosition());
            onClickAdapterObj.onClickfromViewHolder(reviewObj);
        }
    }

    public void updateReviews(ArrayList<reviewEntry> newData){
        reviewsList=newData;
    }
}
