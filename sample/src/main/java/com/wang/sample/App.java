package com.wang.sample;

import android.app.Application;

import com.wkh.widget.config.BubbleConfig;

/**
 * Created by wang on 2017/6/6.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BubbleConfig.MAX_DEFAULT_HEIGHT = 600;
        BubbleConfig.MAX_DEFAULT_WIDTH = 400;
        BubbleConfig.isDebug = true;
    }
}
