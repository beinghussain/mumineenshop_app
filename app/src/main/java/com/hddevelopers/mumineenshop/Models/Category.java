package com.hddevelopers.mumineenshop.Models;

import java.io.Serializable;

/**
 * Created by hussaindehgamwala on 3/4/18.
 */

public class Category implements Serializable {



    private String category;
    private int category_id;
    private int count;
    private String slug;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
