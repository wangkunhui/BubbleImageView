package com.wang.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by wang on 2017/6/1.
 */

public class BubbleImageView extends ImageView {

    private String TAG = "BubbleImageView";

    private static int MAX_DEFAULT_WIDTH = 800;

    private static int MAX_DEFAULT_HEIGHT = 1000;

    private int maxWidth;

    private int maxHeight;

    private int imageBubbleRes;

    private int imageDefaultRes;

    private int imageErrorRes;

    private Bitmap defaultBitmap;

    private Bitmap bubbleBitmap;

    public BubbleImageView(Context context) {
        this(context, null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BubbleImageView);
            maxWidth = ta.getDimensionPixelSize(R.styleable.BubbleImageView_maxWidth, 0);
            maxHeight = ta.getDimensionPixelSize(R.styleable.BubbleImageView_maxHeight, 0);
            imageBubbleRes = ta.getResourceId(R.styleable.BubbleImageView_imageBubble, 0);
            imageDefaultRes = ta.getResourceId(R.styleable.BubbleImageView_imageDefault, 0);
            imageErrorRes = ta.getResourceId(R.styleable.BubbleImageView_imageError, 0);

            ta.recycle();
        }

        setDefaultData();
    }

    /**
     * @param url    网络图片地址
     * @param width  图片原始宽度
     * @param height 图片原始高度
     */
    public void setImage(String url, int width, int height) {

        //根据图片的原始宽高比换算在最大展示区域内的图片大小
        if (0 != height && 0 != width) {
            // 要保证图片的长宽比不变
            double ratio = (double) height / width;
            double ratioDefault = (double) maxHeight / maxWidth;

            if (ratio > ratioDefault) {
                maxHeight = (height > maxHeight ? maxHeight : height);
                maxWidth = (int) (maxHeight / ratio);
            } else {
                maxWidth = (width > maxWidth ? maxWidth : width);
                maxHeight = (int) (maxWidth * ratio);
            }
        }

        new BubbleImageTask().execute(getDefaultBitmap(), getBubbleBitmap());

        if (!TextUtils.isEmpty(url)) {
            Glide.with(getContext()).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource != null) {
                        new BubbleImageTask().execute(resource, getBubbleBitmap());
                    }
                }
            });
        }
    }


    private Bitmap getDefaultBitmap() {
        if (defaultBitmap == null) {
            defaultBitmap = BitmapFactory.decodeResource(getResources(), imageDefaultRes);
        }
        return defaultBitmap;
    }

    private Bitmap getBubbleBitmap() {
        if (bubbleBitmap == null) {
            bubbleBitmap = BitmapFactory.decodeResource(getResources(), imageBubbleRes);
        }
        return bubbleBitmap;
    }

    private void setDefaultData() {

        if (maxWidth == 0) {
            maxWidth = MAX_DEFAULT_WIDTH;
        }

        if (maxHeight == 0) {
            maxHeight = MAX_DEFAULT_HEIGHT;
        }

        if (imageDefaultRes == 0) {
            imageDefaultRes = R.mipmap.ic_default_picture;
        }

        if (imageErrorRes == 0) {
            imageErrorRes = imageDefaultRes;
        }

        if (imageBubbleRes == 0) {
            imageBubbleRes = R.drawable.ic_chat_from_bubble_normal;
        }
    }

    private class BubbleImageTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            if (params == null) {
                Log.e(TAG, "params is null!");
                return null;
            }

            Bitmap sourceBitmap = null;
            Bitmap bgBitmap = null;
            if (params.length == 2) {
                sourceBitmap = params[0];
                bgBitmap = params[1];
            }

            if (sourceBitmap == null || sourceBitmap.isRecycled()) {
                Log.e(TAG, "SourceBitmap is null or recycled!");
                return null;
            }

            if (bgBitmap == null || bgBitmap.isRecycled()) {
                Log.e(TAG, "BgBitmap is null or recycled!");
                return null;
            }

            final Bitmap bp = BitmapUtil.getRoundCornerImage(bgBitmap, sourceBitmap, maxWidth, maxHeight);
            final Bitmap bp2 = BitmapUtil.getShardImage(bgBitmap, bp, maxWidth, maxHeight);

            return bp2;
        }

        protected void onPostExecute(Bitmap bitmap) {

            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            if (BubbleImageView.this != null) {
                BubbleImageView.this.setImageDrawable(drawable);
            }
        }
    }
}
