package com.example.javafinal.com.Users;

import static com.example.javafinal.com.Users.SettingsActivity.PREFS_FILE;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.javafinal.R;
import com.example.javafinal.com.Model.Products;
import com.example.javafinal.com.ViewHolder.ProductViewHolder;
import com.example.javafinal.com.example.shop.LoginActivity;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewManager;
    DatabaseReference prodRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prodRef = FirebaseDatabase.getInstance().getReference().child("Products");

        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences settings = getSharedPreferences(PREFS_FILE,MODE_PRIVATE);
        TextView userName = findViewById(R.id.userProfileName);
        //userName.setText(settings.getString(PREF_PHONE, ""));
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int items = item.getItemId();
                if (items == R.id.nav_cart) {

                } else if (items == R.id.nav_logOut) {
                    SharedPreferences settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear().apply();
                    Intent logOutIntent = new Intent(MainPageActivity.this, LoginActivity.class);
                    startActivity(logOutIntent);
                } else if (items == R.id.nav_settings) {
                    Intent settingsIntent = new Intent(MainPageActivity.this, SettingsActivity.class);
                    startActivity(settingsIntent);

                }
                return false;
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_view, R.id.nav_settings, R.id.nav_)
                .setOpenableLayout(drawer)
                .build();

         */
        recyclerView = findViewById(R.id.recycleMenu);
        recyclerView.setHasFixedSize(true);
        recyclerViewManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> productsList = new FirebaseRecyclerOptions.Builder<Products>().
                setQuery(prodRef, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(productsList) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.productName.setText(model.getname());
                holder.productDescription.setText(model.getDescription());
                holder.productPrice.setText("Цена:"+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.image);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.END);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }
}