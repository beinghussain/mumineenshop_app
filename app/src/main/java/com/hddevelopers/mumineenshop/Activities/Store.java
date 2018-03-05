package com.hddevelopers.mumineenshop.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hddevelopers.mumineenshop.Adapters.ProductAdapter;
import com.hddevelopers.mumineenshop.Helpers.SpacesItemDecoration;
import com.hddevelopers.mumineenshop.Models.Product;
import com.hddevelopers.mumineenshop.Models.StoreData;
import com.hddevelopers.mumineenshop.Models.StoreItem;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.category;
import static com.hddevelopers.mumineenshop.Activities.MainActivity.SHOP_API;

public class Store extends AppCompatActivity {

    private StoreData store_data;
    TextView storeName,storeCity,storePhone;
    CircleImageView storeImage;
    RecyclerView store_product_list;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        StoreItem store = (StoreItem) getIntent().getSerializableExtra("store");

        Fonty.setFonts(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(store.getStore_name());

        Fonty.setFonts(toolbar);

        storeImage = (CircleImageView) findViewById(R.id.store_image);
        storeName = (TextView) findViewById(R.id.store_name);
        storeCity = (TextView) findViewById(R.id.store_city);
        storePhone = (TextView) findViewById(R.id.store_phone);
        store_product_list = (RecyclerView) findViewById(R.id.store_product_list);
        store_product_list.setLayoutManager(new GridLayoutManager(this,2));
        store_product_list.addItemDecoration(new SpacesItemDecoration(24));
        store_product_list.setNestedScrollingEnabled(false);

        backButton = (ImageButton) findViewById(R.id.backbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fetchStoreData(store.getStore_name());

    }

    private void fetchStoreData(final String store_name) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = SHOP_API + "/fetch_store_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        store_data = gson.fromJson(response, new TypeToken<StoreData>() {
                        }.getType());
                        storeName.setText(store_data.getStore().getStore_name());
                        storeCity.setText(store_data.getStore().getCity());
                        storePhone.setText(store_data.getStore().getPhone());
                        if (!store_data.getStore().getStore_logo().equals("")) {
                            Glide.with(Store.this).load(store_data.getStore().getStore_logo())
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(storeImage);
                        }
                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),store_data.getProducts());
                        store_product_list.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("store_link", String.valueOf(store_name));
                return params;
            }
        };;
        queue.add(stringRequest);
    }
}
