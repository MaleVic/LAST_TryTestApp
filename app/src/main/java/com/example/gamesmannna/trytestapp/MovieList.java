package com.example.gamesmannna.trytestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gamesmannna.trytestapp.Interface.ItemClickListener;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.ViewHolder.MovieViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MovieList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference movieList;

    String categoryId="";

    FirebaseRecyclerAdapter<Movie, MovieViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        database = FirebaseDatabase.getInstance();
        movieList = database.getReference("Movie");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    if(getIntent() != null)
        categoryId = getIntent().getStringExtra("Category");
    if(!categoryId.isEmpty() && categoryId != null)
    {
        loadListMovie(categoryId);
    }}

    private void loadListMovie(String categoryId)
    {
        Query searchByName = movieList.orderByChild("MenuID").equalTo(categoryId);

        FirebaseRecyclerOptions<Movie> movieOptions = new FirebaseRecyclerOptions.Builder<Movie>()
                .setQuery(searchByName, Movie.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Movie, MovieViewHolder>(movieOptions) {
            @Override
            protected void onBindViewHolder( MovieViewHolder viewHolder, int position, Movie model) {
                viewHolder.movie_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.movie_image);
                final Movie local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
                        movieDetail.putExtra("MovieId", adapter.getRef(position).getKey());
                        startActivity(movieDetail);
                    }
                });
            }


            @Override
            public MovieViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_item,parent, false);
                return new MovieViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
};
