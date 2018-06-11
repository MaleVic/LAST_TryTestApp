package com.example.gamesmannna.trytestapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    TextView movie_name, movie_price, movie_description;
    ImageView movie_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String movieId="";

    FirebaseDatabase database;
    DatabaseReference movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        database = FirebaseDatabase.getInstance();
        movies = database.getReference("Movie");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_name = (TextView) findViewById(R.id.movie_name);
        movie_price = (TextView) findViewById(R.id.movie_price);
        movie_image = (ImageView) findViewById(R.id.img_movie);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        if(getIntent() != null)
            movieId = getIntent().getStringExtra("MovieId");
        if(!movieId.isEmpty())
        {
            getDetailMovie(movieId);
        }
    }

    private void getDetailMovie(String movieId) {

        movies.child(movieId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Movie movie = dataSnapshot.getValue(Movie.class);

                Picasso.with(getBaseContext()).load(movie.getImage())
                        .into(movie_image);

                collapsingToolbarLayout.setTitle(movie.getName());

                movie_price.setText(movie.getPrice());

                movie_name.setText(movie.getName());

                movie_description.setText(movie.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
