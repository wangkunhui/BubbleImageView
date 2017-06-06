package com.wkh.widget.loader;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by wang on 2017/6/6.
 */

public abstract class ImageLoader implements Serializable {

    protected Context context;

    protected Object path;

    protected ImageLoaderCallBack callBack;

    public ImageLoader(Context context, Object path, ImageLoaderCallBack callBack) {
        this.context = context;
        this.path = path;
        this.callBack = callBack;
    }

    public abstract void loadImageBitmap();

}
