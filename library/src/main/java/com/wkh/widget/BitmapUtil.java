package com.wkh.widget;

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

    public static Bitmap getBubbleBitmap(Bitmap bubble, Bitmap source, int width, int height) {

        Bitmap roundCornerImage = getRoundCornerImage(bubble, source, width, height);
        return getShardImage(bubble, roundCornerImage, width, height);
    }

    public static Bitmap getBlurBitmap(Bitmap source, int color) {

        Bitmap pressedBitmap = source.copy(Bitmap.Config.ARGB_8888, true);
        Canvas cv = new Canvas(pressedBitmap);

        cv.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        return pressedBitmap;
    }

    private static Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in, int width, int height) {
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

    private static Bitmap getShardImage(Bitmap bubble, Bitmap source, int width, int height) {
        Bitmap roundCornerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bubble, bubble.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(1, 1, width - 1, height - 1);
        canvas.drawBitmap(source, rect, rect2, paint);
        return roundCornerImage;
    }
}
