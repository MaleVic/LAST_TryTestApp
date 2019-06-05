package com.example.gamesmannna.trytestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamesmannna.trytestapp.Interface.ItemClickListener;
import com.example.gamesmannna.trytestapp.Model.Category;
import com.example.gamesmannna.trytestapp.ViewHolder.CategoryAdapter;
import com.example.gamesmannna.trytestapp.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.example.gamesmannna.trytestapp.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;

    List<Category> _categoryList = new ArrayList<>();
    TextView txtFullName;

  //  FirebaseRecyclerAdapter adapter;
    private Boolean _isMenuLoad = false;
    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;
    Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = Home.this;

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Меню пользователя");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        Common.ApiService.GetCategoryColl().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.i("GetCategories", response.message());
                _categoryList = response.body();

                loadMenu();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("GetCategories", t.getMessage());
            }
        });

//        while(!_isMenuLoad)
//            {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this, Cart.class);
                startActivity(cartIntent);
            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//        View headerView = navigationView.getHeaderView(0);
//        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
//        txtFullName.setText(Common.CURRENT_USER.getName());
//
//        recyler_menu = (RecyclerView) findViewById(R.id.recyler_menu);
//        recyler_menu.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyler_menu.setLayoutManager(layoutManager);
//            loadMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
//       if(adapter!=null&&!_isMenuLoad) {
//           adapter.startListening();
//       }
    }

    private void loadMenu() {

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class)
                .build();


        LayoutInflater layoutInflater = getLayoutInflater();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.CURRENT_USER.getName());

        recyler_menu = (RecyclerView) findViewById(R.id.recyler_menu);
        recyler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyler_menu.setLayoutManager(layoutManager);

        // ViewGroup viewGroup =  findViewById(R.id.menu_item);
//        for (final Category category : _categoryList) {
//
//
//            //drawer.addView(itemView);
//
//
//            View itemView = layoutInflater
//                    .inflate(R.layout.menu_item, recyler_menu, false);
//            MenuViewHolder viewHolder = new MenuViewHolder(itemView);
//            viewHolder.txtMenuName.setText(category.getName());
//            if (category.getImage() != null) {
//                try {
//                    Picasso.with(getBaseContext()).load(category.getImage())
//                            .into(viewHolder.imageView);
//                } catch (Exception ex) {
//                    Log.e("Category", ex.toString());
//                }
//            }
//            if (category.getName() == null)
//                category.setName("");
//            final Category clickItem = category;
//            viewHolder.setItemClickListener(new ItemClickListener() {
//                @Override
//                public void onClick(View view, int position, boolean isLongClick) {
//                    //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                    Intent movieList = new Intent(Home.this, MovieList.class);
//                    Integer catId = category.getCategoryID();
//                    movieList.putExtra("Category", catId.toString());
//                    startActivity(movieList);
//                }
//            });
//
//
//            recyler_menu.addView((itemView));
//
//        }

        CategoryAdapter categoryAdapter = new CategoryAdapter(getBaseContext(),_categoryList) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item, parent, false);
                return new MenuViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MenuViewHolder viewHolder= (MenuViewHolder) holder;
                viewHolder.txtMenuName.setText(_categoryList.get(position).getName());
                String imagePath=_categoryList.get(position).getImage();
                Picasso.with(getBaseContext()).load(imagePath)
                        .into(viewHolder.imageView);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent movieList = new Intent(Home.this, MovieList.class);
                        Integer categoryId=_categoryList.get(position).getCategoryID();
                        movieList.putExtra("Category", categoryId.toString());
                        startActivity(movieList);
                    }
                });
            }
        };

        recyler_menu.setAdapter(categoryAdapter);
        //recyler_menu.setAdapter(categoryAdapter);

//        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
//
//            @Override
//            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.menu_item, parent, false);
//                return new MenuViewHolder(itemView);
//            }
//
//            @Override
//            protected void onBindViewHolder(MenuViewHolder viewHolder, int positionc) {
//                viewHolder.txtMenuName.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage())
//                        .into(viewHolder.imageView);
//                final Category clickItem = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        //Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent movieList = new Intent(Home.this, MovieList.class);
//                        movieList.putExtra("Category", adapter.getRef(position).getKey());
//                        startActivity(movieList);
//                    }
//                });
//            }
//        };
//        if (!_isMenuLoad) {
//            recyler_menu.setAdapter(adapter);
//        }
//      //  adapter.startListening();
//

        _isMenuLoad = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        // adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this, Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Home.this, OrderStatus.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_log_out) {

            Intent signIn = new Intent(Home.this, SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}