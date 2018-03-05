package com.hddevelopers.mumineenshop.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hddevelopers.mumineenshop.Adapters.HomepageCategory;
import com.hddevelopers.mumineenshop.Helpers.ExpandableGridViewList;
import com.hddevelopers.mumineenshop.Models.Category;
import com.hddevelopers.mumineenshop.R;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.marcinorlowski.fonty.Fonty;

import java.io.Serializable;
import java.util.ArrayList;

import eu.giovannidefrancesco.easysharedprefslib.IStorage;
import eu.giovannidefrancesco.easysharedprefslib.SharedPreferenceStorage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHOP_API = "https://mumineenshop.com/shop_api/";
    private ExpandableGridViewList gridView;
    MaterialSearchBar materialSearchBar;
    private ArrayList<Category> categories;
    private IStorage storage;
    private RelativeLayout storeListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        storage = new SharedPreferenceStorage(this, "MainActivityPrefs");
        categories = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("mumineenshop");

        gridView = (ExpandableGridViewList) findViewById(R.id.grid_view);
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setExpanded(true);
        gridView.setFocusable(false);

        storeListButton = (RelativeLayout) findViewById(R.id.store_list_button);
        storeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),StoreList.class);
                startActivity(myIntent);
            }
        });



        fetchCategories();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Category c = categories.get(position);
                Intent myIntent = new Intent(view.getContext(),ProductList.class);
                myIntent.putExtra("category", (Serializable) c);
                startActivity(myIntent);
            }
        });


    }

    private void fetchCategories() {
        if(storage.get("categories","").equals("")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = SHOP_API + "/shop_fetch_cat.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            storage.store("categories",response);
                            Gson gson = new Gson();
                            categories = gson.fromJson(response, new TypeToken<ArrayList<Category>>() {
                            }.getType());
                            gridView.setAdapter(new HomepageCategory(getApplicationContext(), categories));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);
        } else {
            Gson gson = new Gson();
            String response = storage.get("categories","");
            categories = gson.fromJson(response, new TypeToken<ArrayList<Category>>() {
            }.getType());
            gridView.setAdapter(new HomepageCategory(getApplicationContext(), categories));
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
