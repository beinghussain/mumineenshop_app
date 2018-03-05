package com.hddevelopers.mumineenshop.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hddevelopers.mumineenshop.Models.Product;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

public class SingleProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Product product = (Product) getIntent().getSerializableExtra("product");

        mTitle.setText(product.getName());

        Fonty.setFonts(this);
        Fonty.setFonts(toolbar);

    }

}
