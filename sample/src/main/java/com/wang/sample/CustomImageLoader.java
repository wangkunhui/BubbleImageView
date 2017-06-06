package com.wang.sample;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wkh.widget.ImageLoader;
import com.wkh.widget.ImageLoaderCallBack;

/**
 * Created by wang on 2017/6/6.
 */

public class CustomImageLoader extends ImageLoader {

    public CustomImageLoader(Context context, Object path, ImageLoaderCallBack callBack) {
        super(context, path, callBack);
    }

    @Override
    public void loadImageBitmap() {
        Glide.with(context).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    callBack.onLoadFinish(resource);
                }
            }
        });
    }
}
