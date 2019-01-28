package lib;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u001a\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u001e\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tJ\u001e\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"J \u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\"J\u000e\u0010)\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"J\u0016\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\u0007J>\u0010-\u001a\u0004\u0018\u00010\t2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010/2\u0006\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000f2\u0006\u00102\u001a\u0002032\u000e\u00104\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010/J\u001e\u00105\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u00106\u001a\u00020\u000f2\u0006\u00107\u001a\u00020\u000fJ\u0016\u00108\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u00107\u001a\u00020\u000fJ\u0016\u00109\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010:\u001a\u00020\u0007J\u0016\u0010;\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u00106\u001a\u00020\u000fJ\u0016\u0010<\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010=\u001a\u00020\u000fJ&\u0010>\u001a\u00020?2\u0006\u0010%\u001a\u00020&2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010@\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"J\u0016\u0010A\u001a\u00020?2\u0006\u0010B\u001a\u00020C2\u0006\u0010\u0006\u001a\u00020\u0007\u00a8\u0006D"}, d2 = {"Llib/GraphicUtil;", "", "()V", "convertSaturation", "Landroid/graphics/drawable/Drawable;", "d", "saturation", "", "cropBitmapImage", "Landroid/graphics/Bitmap;", "image", "range", "Landroid/graphics/Rect;", "cropImage", "exifOrientationToDegrees", "", "exifOrientation", "flipImage", "w", "h", "getBitmapByByte", "byteArray", "", "getBitmapByDrawable", "drawable", "getBitmapInvertImage", "src", "getBitmapMaskImage", "maskedImage", "mask", "isInvert", "", "getBitmapModifyExifInterface", "imagePath", "", "getFileByBitmap", "Ljava/io/File;", "context", "Landroid/content/Context;", "btm", "filename", "getOrientationToDegreesModifyExifInterface", "getRoundedCornerBitmap", "bitmap", "roundPx", "mergeImages", "images", "Ljava/util/ArrayList;", "sizeX", "sizeY", "scale", "", "rects", "resizeImage", "wid", "hei", "resizeImageByHeight", "resizeImageByScale", "pct", "resizeImageByWidth", "rotateImage", "degree", "saveImageForAlbum", "", "imageName", "setSaturation", "v", "Landroid/widget/ImageView;", "app_debug"})
public final class GraphicUtil {
    public static final lib.GraphicUtil INSTANCE = null;
    
    public final void setSaturation(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView v, float saturation) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.drawable.Drawable convertSaturation(@org.jetbrains.annotations.NotNull()
    android.graphics.drawable.Drawable d, float saturation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getBitmapByByte(@org.jetbrains.annotations.NotNull()
    byte[] byteArray) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getBitmapByDrawable(@org.jetbrains.annotations.NotNull()
    android.graphics.drawable.Drawable drawable) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getFileByBitmap(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap btm, @org.jetbrains.annotations.NotNull()
    java.lang.String filename) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getBitmapMaskImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap maskedImage, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap mask, boolean isInvert) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getBitmapInvertImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap src) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap mergeImages(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.Object> images, int sizeX, int sizeY, double scale, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<android.graphics.Rect> rects) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getRoundedCornerBitmap(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap, float roundPx) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap getBitmapModifyExifInterface(@org.jetbrains.annotations.NotNull()
    java.lang.String imagePath) {
        return null;
    }
    
    public final int getOrientationToDegreesModifyExifInterface(@org.jetbrains.annotations.NotNull()
    java.lang.String imagePath) {
        return 0;
    }
    
    public final int exifOrientationToDegrees(int exifOrientation) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap resizeImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int wid, int hei) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap resizeImageByWidth(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int wid) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap resizeImageByHeight(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int hei) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap resizeImageByScale(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, float pct) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap flipImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int w, int h) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap rotateImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, int degree) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap cropBitmapImage(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap image, @org.jetbrains.annotations.NotNull()
    android.graphics.Rect range) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap cropImage(@org.jetbrains.annotations.NotNull()
    android.graphics.drawable.Drawable image, @org.jetbrains.annotations.NotNull()
    android.graphics.Rect range) {
        return null;
    }
    
    public final void saveImageForAlbum(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, @org.jetbrains.annotations.NotNull()
    java.lang.String imageName, @org.jetbrains.annotations.NotNull()
    java.lang.String imagePath) {
    }
    
    private GraphicUtil() {
        super();
    }
}