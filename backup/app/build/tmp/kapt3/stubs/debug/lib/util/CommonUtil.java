package lib.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010J\u0016\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0010J\u001e\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fJ\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\fJ=\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f\u00a2\u0006\u0002\u0010#J\u000e\u0010$\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0010J\u0006\u0010(\u001a\u00020)J\u0010\u0010*\u001a\u00020\u00102\b\u0010+\u001a\u0004\u0018\u00010\u0010J\u0016\u0010,\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u00020&J\u0016\u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u00020&2\u0006\u00101\u001a\u00020\fJ\u001c\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0010\u0018\u0001032\u0006\u00104\u001a\u00020\u0010J\u000e\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\fJ\u001c\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u001008J\u0016\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\u0006\u00109\u001a\u00020\fJ\u0006\u0010:\u001a\u00020)J\u0016\u0010;\u001a\u00020\u00102\u0006\u0010<\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u0010J\u000e\u0010=\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0010J\u0016\u0010>\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010?\u001a\u00020\u0010J\u0016\u0010@\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\f2\u0006\u0010B\u001a\u00020\fJ\u000e\u0010C\u001a\u00020 2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010D\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\fH\u0002J\u0018\u0010E\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\f\u00a8\u0006G"}, d2 = {"Llib/util/CommonUtil;", "", "()V", "clearApplicationCache", "", "context", "Landroid/content/Context;", "_dir", "Ljava/io/File;", "getColorArray", "", "div", "", "getColorByString", "", "cororCode", "", "getCurrentTimeCode", "getDateByCode", "yymmdd", "key", "getDays", "year", "mon", "day", "getDaysInMonth", "getEqualRatioRect", "Landroid/graphics/Rect;", "mc", "tw", "th", "smallResize", "", "dfWid", "dfHei", "(Landroid/graphics/Rect;IILjava/lang/Boolean;II)Landroid/graphics/Rect;", "getMarketUrl", "getNumberByCode16", "", "code", "getOnlyEngInput", "Landroid/text/InputFilter;", "getOnlyNum", "str", "getPathFromUri", "uri", "Landroid/net/Uri;", "getPriceStr", "number", "l", "getQurry", "", "qurryString", "getRandomInt", "range", "exceptA", "Ljava/util/ArrayList;", "except", "getRestrictSpecialInput", "getTimeStr", "t", "getUniqueNameByKey", "goLink", "link", "intToText", "n", "len", "isDeviceOnline", "isLeapYear", "readTextFileFromRawResource", "resourceId", "app_debug"})
public final class CommonUtil {
    public static final lib.util.CommonUtil INSTANCE = null;
    
    public final boolean isDeviceOnline(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String readTextFileFromRawResource(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int resourceId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeStr(int t, @org.jetbrains.annotations.NotNull()
    java.lang.String div) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPathFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMarketUrl(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final void goLink(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String link) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.text.InputFilter getRestrictSpecialInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.text.InputFilter getOnlyEngInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOnlyNum(@org.jetbrains.annotations.Nullable()
    java.lang.String str) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final int[] getColorArray(int div) {
        return null;
    }
    
    public final void clearApplicationCache(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    java.io.File _dir) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPriceStr(float number) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPriceStr(float number, int l) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentTimeCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUniqueNameByKey(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.lang.String> getQurry(@org.jetbrains.annotations.NotNull()
    java.lang.String qurryString) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String intToText(int n, int len) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDateByCode(int yymmdd, @org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return null;
    }
    
    public final int getDays(int year, int mon, int day) {
        return 0;
    }
    
    public final int getDaysInMonth(int year, int mon) {
        return 0;
    }
    
    public final int getRandomInt(int range) {
        return 0;
    }
    
    public final int getRandomInt(int range, int except) {
        return 0;
    }
    
    public final int getRandomInt(int range, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<java.lang.String> exceptA) {
        return 0;
    }
    
    private final boolean isLeapYear(int year) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final float[] getColorByString(@org.jetbrains.annotations.NotNull()
    java.lang.String cororCode) {
        return null;
    }
    
    public final float getNumberByCode16(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Rect getEqualRatioRect(@org.jetbrains.annotations.NotNull()
    android.graphics.Rect mc, int tw, int th, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean smallResize, int dfWid, int dfHei) {
        return null;
    }
    
    private CommonUtil() {
        super();
    }
}