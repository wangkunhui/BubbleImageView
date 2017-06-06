package com.wkh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.wang.sample.custom.R;
import com.wkh.widget.config.BubbleConfig;
import com.wkh.widget.loader.ImageLoader;
import com.wkh.widget.loader.ImageLoaderCallBack;
import com.wkh.widget.util.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/6/1.
 */

public class BubbleImageView extends ImageView implements ImageLoaderCallBack {

    private String TAG = "BubbleImageView";

    private int maxWidth;

    private int maxHeight;

    private int imageBubbleRes;

    private int imageDefaultRes;

    private int imageErrorRes;

    private Bitmap defaultBitmap;

    private Bitmap bubbleBitmap;

    private int pressedColor;

    private boolean isPressedFeedback = false;

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
            pressedColor = ta.getColor(R.styleable.BubbleImageView_pressedColor, 0);
            ta.recycle();
        }

        this.setClickable(true);
        setDefaultData();
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

    /**
     * @param width  图片原始宽度
     * @param height 图片原始高度
     */
    public void setImage(int width, int height, ImageLoader imageLoader) {

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

        imageLoader.loadImageBitmap();

    }

    private void setDefaultData() {

        if (maxWidth == 0) {
            maxWidth = BubbleConfig.MAX_DEFAULT_WIDTH;
        }

        if (maxHeight == 0) {
            maxHeight = BubbleConfig.MAX_DEFAULT_HEIGHT;
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

        if (pressedColor == 0) {
            isPressedFeedback = false;
        } else {
            isPressedFeedback = true;
        }
    }

    @Override
    public void onLoadFinish(Bitmap bitmap) {
        new BubbleImageTask().execute(bitmap, getBubbleBitmap());
    }

    private class BubbleImageTask extends AsyncTask<Bitmap, Integer, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(Bitmap... params) {

            Bitmap sourceBitmap = null, bgBitmap = null;

            if (params == null) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "Bitmap params is null!");
                }
                return null;
            }

            if (params.length != 2) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "Params length is not fit!");
                }
                return null;
            }

            sourceBitmap = params[0];
            bgBitmap = params[1];

            if (sourceBitmap == null || sourceBitmap.isRecycled()) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "SourceBitmap is null or recycled!");
                }
                return null;
            }

            if (bgBitmap == null || bgBitmap.isRecycled()) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "BgBitmap is null or recycled!");
                }
                return null;
            }

            ArrayList<Bitmap> results = new ArrayList<>();

            Bitmap normal = BitmapUtil.getBubbleBitmap(bgBitmap, sourceBitmap, maxWidth, maxHeight);

            results.add(normal);

            if (isPressedFeedback) {
                Bitmap pressed = BitmapUtil.getBlurBitmap(normal, pressedColor);
                results.add(pressed);
            }

            return results;
        }

        protected void onPostExecute(List<Bitmap> bitmaps) {

            if (BubbleImageView.this == null) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "BubbleImageView is destroyed!");
                }
                return;
            }

            if (bitmaps == null || bitmaps.size() == 0) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "Bitmaps is null or empty!");
                }
                return;
            }

            Bitmap normal = bitmaps.get(0);

            if (normal == null || normal.isRecycled()) {
                if (BubbleConfig.isDebug) {
                    Log.e(TAG, "Normal bitmap is null or empty!");
                }
                return;
            }

            if (isPressedFeedback) {
                if (bitmaps.size() == 2) {

                    Bitmap pressed = bitmaps.get(1);

                    if (pressed == null || pressed.isRecycled()) {
                        if (BubbleConfig.isDebug) {
                            Log.e(TAG, "Pressed bitmap is null or empty!");
                        }
                        return;
                    }

                    Drawable normalDrawable = new BitmapDrawable(getResources(), normal);
                    Drawable pressedDrawable = new BitmapDrawable(getResources(), pressed);

                    StateListDrawable stateListDrawable = new StateListDrawable();
                    stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);//有状态的必须写在上面
                    stateListDrawable.addState(new int[]{}, normalDrawable);//没有状态的必须写在下面

                    BubbleImageView.this.setImageDrawable(stateListDrawable);
                } else {
                    if (BubbleConfig.isDebug) {
                        Log.e(TAG, "Need feedback but bitmaps size error!");
                    }
                }
            } else {
                BubbleImageView.this.setImageBitmap(normal);
            }
        }
    }
}
