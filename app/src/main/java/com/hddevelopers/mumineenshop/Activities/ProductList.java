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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.hddevelopers.mumineenshop.Adapters.ProductAdapter;
import com.hddevelopers.mumineenshop.Helpers.ExpandableGridViewList;
import com.hddevelopers.mumineenshop.Helpers.SpacesItemDecoration;
import com.hddevelopers.mumineenshop.Models.Category;
import com.hddevelopers.mumineenshop.Models.Product;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.hddevelopers.mumineenshop.Activities.MainActivity.SHOP_API;

public class ProductList extends AppCompatActivity {
    Category category;
    private ArrayList<Product> products;
    private RecyclerView productGridList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Fonty.setFonts(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Fonty.setFonts(toolbar);
        category = (Category) getIntent().getSerializableExtra("category");
        mTitle.setText(category.getCategory());
        productGridList = (RecyclerView) findViewById(R.id.product_list);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        productGridList.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        productGridList.setLayoutManager(manager);

        fetchProductList();
    }

    private void fetchProductList() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = SHOP_API + "/shop_fetch_products.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        products = gson.fromJson(response, new TypeToken<ArrayList<Product>>() {
                        }.getType());
                        productGridList.setAdapter(new ProductAdapter(getApplicationContext(),products));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductList.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("cat_id", String.valueOf(category.getCategory_id()));
                return params;
            }
        };;
        queue.add(stringRequest);
    }

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

}
