package com.hddevelopers.mumineenshop.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
import android.widget.ImageView;
        import android.widget.TextView;

        import com.hddevelopers.mumineenshop.Models.Category;
        import com.hddevelopers.mumineenshop.R;
        import com.marcinorlowski.fonty.Fonty;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomepageCategory extends BaseAdapter {
    private Context mContext;
    private ArrayList<Category> categories;


    public HomepageCategory(Context c, ArrayList<Category> categories){
        mContext = c;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getCategory_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = categories.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.homepage_cat, null);
            Fonty.setFonts((ViewGroup) convertView);
        }

        final TextView nameTextView = convertView.findViewById(R.id.category_text);
        final ImageView imageViewFavorite = convertView.findViewById(R.id.category_img);

        String uri = "@drawable/"+category.getSlug().replace("-","_");
        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());

        Drawable res = mContext.getResources().getDrawable(imageResource);
        imageViewFavorite.setImageDrawable(res);
        nameTextView.setText(category.getCategory());
        return convertView;
    }

}