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

import com.example.gamesmannna.trytestapp.Common.Common;
import com.example.gamesmannna.trytestapp.Interface.ItemClickListener;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.ViewHolder.Constants;
import com.example.gamesmannna.trytestapp.ViewHolder.MovieAdapter;
import com.example.gamesmannna.trytestapp.ViewHolder.MovieViewHolder;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    //FirebaseDatabase database;
    //DatabaseReference movieList;


    List<Movie> _movieList =new ArrayList<>();
    String categoryId="";

    //FirebaseRecyclerAdapter<Movie, MovieViewHolder> adapter;

    //FirebaseRecyclerAdapter<Movie, MovieViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

      //  database = FirebaseDatabase.getInstance();
     //   movieList = database.getReference("Movie");

        Common.ApiService.GetMoviesColl().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.i("GetMoviesColl",response.message());
                _movieList=response.body();
                if(getIntent() != null)
                    categoryId = getIntent().getStringExtra("Category");
                if(!categoryId.isEmpty() && categoryId != null)
                {
                loadListMovie(categoryId);
                }

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("GetMoviesColl",t.getMessage());
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//    if(getIntent() != null)
//        categoryId = getIntent().getStringExtra("Category");
//    if(!categoryId.isEmpty() && categoryId != null)
//    {
////        loadListMovie(categoryId);
//    }

    materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
    materialSearchBar.setHint("Введите название фильма");

    //loadSuggest();

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
//            if (!enabled)
//                recyclerView.setAdapter(adapter);
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

       // Query searchByName = movieList.orderByChild("Name").equalTo(text.toString());

        MovieAdapter movieAdapter=new MovieAdapter(getBaseContext(),_movieList) {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_item,parent, false);
                MovieViewHolder movieViewHolder= new MovieViewHolder(itemView);
                movieViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
                        movieDetail.putExtra(Constants.MOVIE_ID, _movieList.get(position).getMovieID());
                        startActivity(movieDetail);
                    }
                });
                return movieViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MovieViewHolder viewHolder= (MovieViewHolder) holder;
                viewHolder.movie_name.setText(_movieList.get(position).getName());
                String imageString=_movieList.get(position).getImage();
                Picasso.with(getBaseContext()).load(imageString)
                        .into(viewHolder.movie_image);
            }
        };

        recyclerView.setAdapter(movieAdapter);

//        FirebaseRecyclerOptions<Movie> movieOptions = new FirebaseRecyclerOptions.Builder<Movie>()
//                .setQuery(searchByName, Movie.class)
//                .build();
//
    //    searchAdapter = new FirebaseRecyclerAdapter<Movie, MovieViewHolder>(movieOptions) {
//            @Override
//            protected void onBindViewHolder(MovieViewHolder viewHolder, int position, Movie model) {
//                viewHolder.movie_name.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage())
//                        .into(viewHolder.movie_image);
//            }
//
//            @NonNull
//            @Override
//            public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.movie_item,parent, false);
//                MovieViewHolder movieViewHolder= new MovieViewHolder(itemView);
//                movieViewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//
//                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
//                        movieDetail.putExtra(Constants.MOVIE_ID, searchAdapter.getRef(position).getKey());
//                        startActivity(movieDetail);
//                    }
//                });
//                return movieViewHolder;
//            }
//        };
//        searchAdapter.startListening();
//        recyclerView.setAdapter(searchAdapter);
    }

//    private void loadSuggest() {
//
//        movieList.orderByChild("MenuID").equalTo(categoryId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
//                        {
//                            Movie item = postSnapshot.getValue(Movie.class);
//                            suggestList.add(item.getName());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }

    private void loadListMovie(String categoryId)
    {
        //Query searchByName = movieList.orderByChild("MenuID").equalTo(categoryId);

//        FirebaseRecyclerOptions<Movie> movieOptions = new FirebaseRecyclerOptions.Builder<Movie>()
//                .setQuery(searchByName, Movie.class)
//                .build();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Movie> movList=new ArrayList<>();
        for(Movie movie:_movieList)
        {
            if(movie.getMenuID().equals(categoryId))
                movList.add(movie);
        }
        final List<Movie> movListFinal=movList;
        MovieAdapter movieAdapter=new MovieAdapter(getBaseContext(),movList) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_item,parent, false);
                return new MovieViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MovieViewHolder viewHolder= (MovieViewHolder) holder;
                viewHolder.movie_name.setText(movListFinal.get(position).getName());
                Picasso.with(getBaseContext()).load(movListFinal.get(position).getImage())
                        .into(viewHolder.movie_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
                        movieDetail.putExtra(Constants.MOVIE_ID,movListFinal.get(position).getMovieID().toString());
                        startActivity(movieDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(movieAdapter);
//        adapter = new FirebaseRecyclerAdapter<Movie, MovieViewHolder>(movieOptions) {
//            @Override
//            protected void onBindViewHolder( MovieViewHolder viewHolder, int position, Movie model) {
//                viewHolder.movie_name.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage())
//                        .into(viewHolder.movie_image);
//                final Movie local = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//
//                        Intent movieDetail = new Intent(MovieList.this, MovieDetail.class);
//                        movieDetail.putExtra(Constants.MOVIE_ID, adapter.getRef(position).getKey());
//                        startActivity(movieDetail);
//                    }
//                });
//            }


//            @Override
//            public MovieViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.movie_item,parent, false);
//                return new MovieViewHolder(itemView);
//            }
//        };
        //adapter.startListening();
       // recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   if(adapter!=null)
    //        adapter.stopListening();
   //     if(searchAdapter!=null)
   //         searchAdapter.stopListening();
    }
};
