package com.hddevelopers.mumineenshop.Models;

import java.util.ArrayList;


public class StoreData {

    private StoreItem store;
    private ArrayList<Product> products;

    public StoreItem getStore() {
        return store;
    }

    public void setStore(StoreItem store) {
        this.store = store;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}
