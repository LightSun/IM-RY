package com.heaven7.android.common.dialog.wrapry_app;

import android.app.Application;

import com.heaven7.android.im.ry.RongyunIm;


public class RyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RongyunIm.init(this);
    }
}
