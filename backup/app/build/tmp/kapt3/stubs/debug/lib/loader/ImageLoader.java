package lib.loader;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 *2\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001:\u0002*+B\u000f\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J#\u0010\u001c\u001a\u0004\u0018\u00010\u00042\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u001e\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0002J\u001a\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020\u00022\u0006\u0010&\u001a\u00020\u0002H\u0002J\u0010\u0010\'\u001a\u00020!2\u0006\u0010(\u001a\u00020\u0004H\u0014J\u0006\u0010)\u001a\u00020!R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u0007R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0002X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\u00a8\u0006,"}, d2 = {"Llib/loader/ImageLoader;", "Landroid/os/AsyncTask;", "", "", "", "delegate", "Llib/loader/ImageLoader$Delegate;", "(Llib/loader/ImageLoader$Delegate;)V", "getDelegate", "()Llib/loader/ImageLoader$Delegate;", "setDelegate", "image", "Landroid/graphics/Bitmap;", "getImage", "()Landroid/graphics/Bitmap;", "setImage", "(Landroid/graphics/Bitmap;)V", "image_path", "getImage_path", "()Ljava/lang/String;", "setImage_path", "(Ljava/lang/String;)V", "size", "Landroid/graphics/Point;", "getSize", "()Landroid/graphics/Point;", "setSize", "(Landroid/graphics/Point;)V", "doInBackground", "params", "", "([Ljava/lang/String;)[B", "loadImg", "", "imgPath", "makeConnection", "Ljava/net/HttpURLConnection;", "uri", "method", "onPostExecute", "result", "removeLoader", "Companion", "Delegate", "app_debug"})
public final class ImageLoader extends android.os.AsyncTask<java.lang.String, java.lang.Integer, byte[]> {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String image_path;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.Bitmap image;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.Point size;
    @org.jetbrains.annotations.Nullable()
    private lib.loader.ImageLoader.Delegate delegate;
    private static final int MAX_CASH_NUM = 20;
    private static final lib.datastructure.IndexMap<java.lang.String, android.graphics.Bitmap> imgCashs = null;
    private static final javax.net.ssl.HostnameVerifier DO_NOT_VERIFY = null;
    public static final lib.loader.ImageLoader.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImage_path() {
        return null;
    }
    
    public final void setImage_path(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getImage() {
        return null;
    }
    
    public final void setImage(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Point getSize() {
        return null;
    }
    
    public final void setSize(@org.jetbrains.annotations.Nullable()
    android.graphics.Point p0) {
    }
    
    public final void removeLoader() {
    }
    
    public final void loadImg(@org.jetbrains.annotations.NotNull()
    java.lang.String imgPath) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    protected byte[] doInBackground(@org.jetbrains.annotations.NotNull()
    java.lang.String... params) {
        return null;
    }
    
    @java.lang.Override()
    protected void onPostExecute(@org.jetbrains.annotations.NotNull()
    byte[] result) {
    }
    
    private final java.net.HttpURLConnection makeConnection(java.lang.String uri, java.lang.String method) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final lib.loader.ImageLoader.Delegate getDelegate() {
        return null;
    }
    
    public final void setDelegate(@org.jetbrains.annotations.Nullable()
    lib.loader.ImageLoader.Delegate p0) {
    }
    
    public ImageLoader(@org.jetbrains.annotations.Nullable()
    lib.loader.ImageLoader.Delegate delegate) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016\u00a8\u0006\b"}, d2 = {"Llib/loader/ImageLoader$Delegate;", "", "onImageLoadCompleted", "", "loader", "Llib/loader/ImageLoader;", "image", "Landroid/graphics/Bitmap;", "app_debug"})
    public static abstract interface Delegate {
        
        public abstract void onImageLoadCompleted(@org.jetbrains.annotations.NotNull()
        lib.loader.ImageLoader loader, @org.jetbrains.annotations.Nullable()
        android.graphics.Bitmap image);
        
        @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 3)
        public static final class DefaultImpls {
            
            public static void onImageLoadCompleted(lib.loader.ImageLoader.Delegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.ImageLoader loader, @org.jetbrains.annotations.Nullable()
            android.graphics.Bitmap image) {
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Llib/loader/ImageLoader$Companion;", "", "()V", "DO_NOT_VERIFY", "Ljavax/net/ssl/HostnameVerifier;", "MAX_CASH_NUM", "", "imgCashs", "Llib/datastructure/IndexMap;", "", "Landroid/graphics/Bitmap;", "clearMemory", "", "removeAllCashs", "app_debug"})
    public static final class Companion {
        
        public final void removeAllCashs() {
        }
        
        public final void clearMemory() {
        }
        
        private Companion() {
            super();
        }
    }
}