package com.wkh.widget;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by wang on 2017/6/6.
 */

public interface ImageLoaderCallBack extends Serializable {

    void onLoadFinish(Bitmap bitmap);

}
