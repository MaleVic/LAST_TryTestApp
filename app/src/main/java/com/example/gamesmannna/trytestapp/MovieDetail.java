package com.example.gamesmannna.trytestapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.gamesmannna.trytestapp.Common.Common;
import com.example.gamesmannna.trytestapp.Database.Database;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.Model.Order;
import com.example.gamesmannna.trytestapp.ViewHolder.Constants;
import com.example.gamesmannna.trytestapp.ViewHolder.VideoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Vector;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity implements View.OnClickListener {


    TextView movie_name, movie_price, movie_description;
    ImageView movie_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    FButton button1;
    ElegantNumberButton numberButton;

    String movieId = "";

    // FirebaseDatabase database;
    // DatabaseReference movies;

    Movie _currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        View v = findViewById(R.id.button1);
        v.setOnClickListener(this);


        //  database = FirebaseDatabase.getInstance();
        //  movies = database.getReference("Movie");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);


        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_name = (TextView) findViewById(R.id.movie_name);
        movie_price = (TextView) findViewById(R.id.movie_price);
        movie_image = (ImageView) findViewById(R.id.img_movie);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        if (getIntent() != null)
            movieId = getIntent().getStringExtra(Constants.MOVIE_ID);
        if (movieId != null && !movieId.isEmpty()) {
            getDetailMovie(movieId);
        }


    }

    private void getDetailMovie(final String movieId) {

        Common.ApiService.GetMovieById(Integer.parseInt(movieId)).enqueue(new Callback<Movie>() {
            @Override



            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.i("GetMovieById", response.message());
                _currentMovie = response.body();
                Picasso.with(getBaseContext()).load(_currentMovie.getImage())
                        .into(movie_image);
                collapsingToolbarLayout.setTitle(_currentMovie.getName());

                movie_price.setText(_currentMovie.getPrice());

                movie_name.setText(_currentMovie.getName());

                movie_description.setText(_currentMovie.getDescription());
                final String movieID = movieId;
                btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Database(getBaseContext()).addToCart(new Order(
                                movieID,
                                _currentMovie.getName(),
                                numberButton.getNumber(),
                                _currentMovie.getPrice(),
                                _currentMovie.getDiscount()
                        ));


                        Toast.makeText(MovieDetail.this, "Добавлено в Корзину", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("GetMovieById", t.getMessage());
            }
        });
//        movies.child(movieId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                _currentMovie = dataSnapshot.getValue(Movie.class);
//
//                Picasso.with(getBaseContext()).load(_currentMovie.getImage())
//                        .into(movie_image);
//
//                collapsingToolbarLayout.setTitle(_currentMovie.getName());
//
//                movie_price.setText(_currentMovie.getPrice());
//
//                movie_name.setText(_currentMovie.getName());
//
//                movie_description.setText(_currentMovie.getDescription());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onClick(View arg0) {

        if (arg0.getId() == R.id.button1) {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, SecondActivity.class);

            //start the second Activity
            this.startActivity(intent);
    }
}
}
