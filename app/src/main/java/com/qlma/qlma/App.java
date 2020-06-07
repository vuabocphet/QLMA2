package com.qlma.qlma;

import android.app.Application;

import com.qlma.qlma.utils.Pref;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Pref.get().init(this);
    }
}
