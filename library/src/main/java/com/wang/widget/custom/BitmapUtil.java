package com.wang.widget.custom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * Created by wang on 2017/6/1.
 */

public class BitmapUtil {

    public static Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in, int width, int height) {
        Bitmap roundCornerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        int width1 = bitmap_in.getWidth();
        int height1 = bitmap_in.getHeight();

        Rect rectF = new Rect(0, 0, width1, height1);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundCornerImage;
    }

    public static Bitmap getShardImage(Bitmap bitmap_bg, Bitmap bitmap_in, int width, int height) {
        Bitmap roundCornerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(1, 1, width - 1, height - 1);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundCornerImage;
    }
}
