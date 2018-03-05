package com.hddevelopers.mumineenshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hddevelopers.mumineenshop.Activities.SingleProduct;
import com.hddevelopers.mumineenshop.Models.Product;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final ArrayList<Product> products;
    private final Context context;

    public ProductAdapter(Context ctx, ArrayList<Product> products){
        this.context =ctx;
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
       final Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("Rs."+product.getPrice());
        Glide.with(context).load(product.getImages().get(0).getThumbnail_url())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SingleProduct.class);
                intent.putExtra("product",product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName,productPrice;
        ImageView productImage;

        ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Fonty.setFonts((ViewGroup) itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.product_image);
            productPrice = itemView.findViewById(R.id.product_price);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
