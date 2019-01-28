package lib.loader;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 %2\u00020\u0001:\u0003%&\'B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0003J\"\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0014J0\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J8\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0003JL\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u00032\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0014JT\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u00032\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00142\u0006\u0010\u001a\u001a\u00020\u0003J\u0016\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0017J\u001a\u0010\u001c\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001d\u001a\u00020\u0003H\u0016J\u0012\u0010\u001e\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u0006\u0010\u001f\u001a\u00020\u0010J\u0010\u0010 \u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003J\u000e\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u0006J\u000e\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u0004\u00a8\u0006("}, d2 = {"Llib/loader/DataManager;", "Llib/loader/DataLoader$Delegate;", "type", "", "(Ljava/lang/String;)V", "dataDelegate", "Llib/loader/DataManager$DataDelegate;", "jsonDelegate", "Llib/loader/DataManager$JsonDelegate;", "loaderA", "", "Llib/loader/DataLoader;", "getType", "()Ljava/lang/String;", "setType", "destory", "", "loadData", "dataUrl", "param", "", "files", "Ljava/util/ArrayList;", "Llib/loader/FileObject;", "boundary", "headers", "contentType", "file", "onCompleted", "result", "onLoadErr", "removeAllLoader", "removeLoader", "setOnDataDelegate", "_dataDelegate", "setOnJsonDelegate", "_jsonDelegate", "Companion", "DataDelegate", "JsonDelegate", "app_debug"})
public final class DataManager implements lib.loader.DataLoader.Delegate {
    private lib.loader.DataManager.JsonDelegate jsonDelegate;
    private lib.loader.DataManager.DataDelegate dataDelegate;
    private java.util.Map<java.lang.String, lib.loader.DataLoader> loaderA;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String type;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DataManager";
    public static final lib.loader.DataManager.Companion Companion = null;
    
    public final void setOnJsonDelegate(@org.jetbrains.annotations.NotNull()
    lib.loader.DataManager.JsonDelegate _jsonDelegate) {
    }
    
    public final void setOnDataDelegate(@org.jetbrains.annotations.NotNull()
    lib.loader.DataManager.DataDelegate _dataDelegate) {
    }
    
    public final void removeAllLoader() {
    }
    
    public final void removeLoader(@org.jetbrains.annotations.Nullable()
    java.lang.String dataUrl) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> param) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    lib.loader.FileObject file) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> param, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<lib.loader.FileObject> files) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> param, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<lib.loader.FileObject> files, @org.jetbrains.annotations.NotNull()
    java.lang.String boundary) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> param, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<lib.loader.FileObject> files, @org.jetbrains.annotations.NotNull()
    java.lang.String boundary, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> headers) {
    }
    
    public final void loadData(@org.jetbrains.annotations.NotNull()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> param, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<lib.loader.FileObject> files, @org.jetbrains.annotations.NotNull()
    java.lang.String boundary, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
    java.lang.String contentType) {
    }
    
    public final void destory() {
    }
    
    @java.lang.Override()
    public void onCompleted(@org.jetbrains.annotations.Nullable()
    java.lang.String dataUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String result) {
    }
    
    @java.lang.Override()
    public void onLoadErr(@org.jetbrains.annotations.Nullable()
    java.lang.String dataUrl) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    public final void setType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public DataManager(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u001a\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016\u00a8\u0006\n"}, d2 = {"Llib/loader/DataManager$DataDelegate;", "", "onDataCompleted", "", "manager", "Llib/loader/DataManager;", "path", "", "result", "onDataLoadErr", "app_debug"})
    public static abstract interface DataDelegate {
        
        public abstract void onDataCompleted(@org.jetbrains.annotations.NotNull()
        lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
        java.lang.String path, @org.jetbrains.annotations.NotNull()
        java.lang.String result);
        
        public abstract void onDataLoadErr(@org.jetbrains.annotations.NotNull()
        lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
        java.lang.String path);
        
        @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 3)
        public static final class DefaultImpls {
            
            public static void onDataCompleted(lib.loader.DataManager.DataDelegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
            java.lang.String path, @org.jetbrains.annotations.NotNull()
            java.lang.String result) {
            }
            
            public static void onDataLoadErr(lib.loader.DataManager.DataDelegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
            java.lang.String path) {
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u001a\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016\u00a8\u0006\f"}, d2 = {"Llib/loader/DataManager$JsonDelegate;", "", "onJsonCompleted", "", "manager", "Llib/loader/DataManager;", "path", "", "result", "Lorg/json/JSONObject;", "onJsonLoadErr", "onJsonParseErr", "app_debug"})
    public static abstract interface JsonDelegate {
        
        public abstract void onJsonCompleted(@org.jetbrains.annotations.NotNull()
        lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
        java.lang.String path, @org.jetbrains.annotations.NotNull()
        org.json.JSONObject result);
        
        public abstract void onJsonParseErr(@org.jetbrains.annotations.NotNull()
        lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
        java.lang.String path);
        
        public abstract void onJsonLoadErr(@org.jetbrains.annotations.NotNull()
        lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
        java.lang.String path);
        
        @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 3)
        public static final class DefaultImpls {
            
            public static void onJsonCompleted(lib.loader.DataManager.JsonDelegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
            java.lang.String path, @org.jetbrains.annotations.NotNull()
            org.json.JSONObject result) {
            }
            
            public static void onJsonParseErr(lib.loader.DataManager.JsonDelegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
            java.lang.String path) {
            }
            
            public static void onJsonLoadErr(lib.loader.DataManager.JsonDelegate $this, @org.jetbrains.annotations.NotNull()
            lib.loader.DataManager manager, @org.jetbrains.annotations.Nullable()
            java.lang.String path) {
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Llib/loader/DataManager$Companion;", "", "()V", "TAG", "", "getTAG$app_debug", "()Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTAG$app_debug() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}