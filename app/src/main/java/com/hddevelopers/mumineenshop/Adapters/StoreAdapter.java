package com.hddevelopers.mumineenshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hddevelopers.mumineenshop.Activities.Store;
import com.hddevelopers.mumineenshop.Models.StoreItem;
import com.hddevelopers.mumineenshop.R;
import com.marcinorlowski.fonty.Fonty;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private Context context;
    private ArrayList<StoreItem> storeItems;

    public StoreAdapter(Context ctx, ArrayList<StoreItem> storeItems) {
        this.storeItems = storeItems;
        this.context = ctx;
    }

    @Override
    public StoreAdapter.StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item_list,parent,false);
        return new StoreAdapter.StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreAdapter.StoreViewHolder holder, int position) {
        final StoreItem storeItem = storeItems.get(position);
        holder.storeName.setText(storeItem.getStore_name());
//        holder.storeProductCount.setText(storeItem.getProducts_count() + " Products");
        if (!storeItem.getStore_logo().equals("")) {
            Glide.with(context).load(storeItem.getStore_logo())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.storeImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Store.class);
                intent.putExtra("store",storeItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder{
        TextView storeName, storeProductCount;
        CircleImageView storeImage;
        public StoreViewHolder(View itemView) {
            super(itemView);
            Fonty.setFonts((ViewGroup) itemView);
            storeName = itemView.findViewById(R.id.store_name);
            storeImage  = itemView.findViewById(R.id.store_image);
        }
    }
}
