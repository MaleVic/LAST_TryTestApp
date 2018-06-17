package com.example.gamesmannna.trytestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference movieList;

    String categoryId="";

    FirebaseRecyclerAdapter<Movie, MovieViewHolder> adapter;

    FirebaseRecyclerAdapter<Movie, MovieViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

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
    }

    materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
    materialSearchBar.setHint("Введите название фильма");

    loadSuggest();

    materialSearchBar.setLastSuggestions(suggestList);
    materialSearchBar.setCardViewElevation(10);
    materialSearchBar.addTextChangeListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<String> suggest = new ArrayList<>();
            for (String search:suggestList)
            {
                if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    suggest.add(search);
            }

            materialSearchBar.setLastSuggestions(suggest);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
    materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
        @Override
        public void onSearchStateChanged(boolean enabled) {
            if (!enabled)
                recyclerView.setAdapter(adapter);
        }

        @Override
        public void onSearchConfirmed(CharSequence text) {
            startSearch(text);
        }

        @Override
        public void onButtonClicked(int buttonCode) {

        }
    });

    }

    private void startSearch(CharSequence text) {

        Query searchByName = movieList.orderByChild("Name").equalTo(text.toString());

        FirebaseRecyclerOptions<Movie> movieOptions = new FirebaseRecyclerOptions.Builder<Movie>()
                .setQuery(searchByName, Movie.class)
                .build();

        searchAdapter = new FirebaseRecyclerAdapter<Movie, MovieViewHolder>(movieOptions) {
            @Override
            protected void onBindViewHolder(MovieViewHolder viewHolder, int position, Movie model) {
                viewHolder.movie_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.movie_image);
            }

            @NonNull
            @Override
            public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_item,parent, false);
                MovieViewHolder movieViewHolder= new MovieViewHolder(itemView);
                movieViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
                        movieDetail.putExtra("MovieID", searchAdapter.getRef(position).getKey());
                        startActivity(movieDetail);
                    }
                });
                return movieViewHolder;
            }
        };
        searchAdapter.startListening();
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        movieList.orderByChild("MenuID").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Movie item = postSnapshot.getValue(Movie.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

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
                        movieDetail.putExtra("MovieID", searchAdapter.getRef(position).getKey());
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
        searchAdapter.stopListening();
    }
};
