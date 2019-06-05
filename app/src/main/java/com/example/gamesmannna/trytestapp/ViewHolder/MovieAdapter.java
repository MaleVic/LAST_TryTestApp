package com.example.gamesmannna.trytestapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.gamesmannna.trytestapp.Model.Category;
import com.example.gamesmannna.trytestapp.Model.Movie;

import java.util.List;

public abstract class MovieAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<MovieViewHolder> {

    public List<Movie> Movies;
    private Context _context;


    public MovieAdapter(Context context, List<Movie> movies) {
        _context = context;
        Movies = movies;
    }



    @Override
    public int getItemCount() {
        return Movies.size();
    }


}
