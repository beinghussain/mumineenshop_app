package com.hddevelopers.mumineenshop.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hddevelopers.mumineenshop.Adapters.HomepageCategory;
import com.hddevelopers.mumineenshop.Adapters.StoreAdapter;
import com.hddevelopers.mumineenshop.Helpers.SpacesItemDecoration;
import com.hddevelopers.mumineenshop.Models.Category;
import com.hddevelopers.mumineenshop.Models.StoreItem;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

import java.util.ArrayList;

import eu.giovannidefrancesco.easysharedprefslib.IStorage;
import eu.giovannidefrancesco.easysharedprefslib.SharedPreferenceStorage;

import static com.hddevelopers.mumineenshop.Activities.MainActivity.SHOP_API;

public class StoreList extends AppCompatActivity {

    private RecyclerView storeList;
    private IStorage storage;
    private ArrayList<StoreItem> storeItems;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        storage = new SharedPreferenceStorage(this, "MainActivityPrefs");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Store List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Fonty.setFonts(this);
        Fonty.setFonts(toolbar);


        storeList = (RecyclerView) findViewById(R.id.store_list);
        storeList.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        storeList.addItemDecoration(new SpacesItemDecoration(16));
        storeItems = new ArrayList<>();

        backButton = (ImageButton) findViewById(R.id.backbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fetchStoreList();

    }

    private void fetchStoreList() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = SHOP_API + "/shop_fetch_store_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        storage.store("storeList",response);
                        Gson gson = new Gson();
                        storeItems = gson.fromJson(response, new TypeToken<ArrayList<StoreItem>>() {
                        }.getType());
                        storeList.setAdapter(new StoreAdapter(getApplicationContext(), storeItems));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
