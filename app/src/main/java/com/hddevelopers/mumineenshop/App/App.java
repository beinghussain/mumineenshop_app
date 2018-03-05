package com.hddevelopers.mumineenshop.App;

import android.app.Application;

import com.marcinorlowski.fonty.Fonty;



public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fonty
                .context(this)
                .regularTypeface("r.ttf")
                .italicTypeface("m.ttf")
                .boldTypeface("s.ttf")
                .done();
    }
}
