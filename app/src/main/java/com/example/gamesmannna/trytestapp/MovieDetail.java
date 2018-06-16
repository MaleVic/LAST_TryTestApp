package com.example.gamesmannna.trytestapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.gamesmannna.trytestapp.Database.Database;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.Model.Order;
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

    Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        database = FirebaseDatabase.getInstance();
        movies = database.getReference("Movie");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        movieId,
                        currentMovie.getName(),
                        numberButton.getNumber(),
                        currentMovie.getPrice(),
                        currentMovie.getDiscount()
                ));

                Toast.makeText(MovieDetail.this, "Добавлено в Корзину", Toast.LENGTH_SHORT).show();
            }
        });

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
                currentMovie = dataSnapshot.getValue(Movie.class);

                Picasso.with(getBaseContext()).load(currentMovie.getImage())
                        .into(movie_image);

                collapsingToolbarLayout.setTitle(currentMovie.getName());

                movie_price.setText(currentMovie.getPrice());

                movie_name.setText(currentMovie.getName());

                movie_description.setText(currentMovie.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
