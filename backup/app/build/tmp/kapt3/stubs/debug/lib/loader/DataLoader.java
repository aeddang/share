package lib.loader;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0000\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0004J#\u0010\u0013\u001a\u0004\u0018\u00010\u00022\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0015\"\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u0016J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0002H\u0014Jb\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\b2\u0014\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\r2\u0006\u0010\u001d\u001a\u00020\u00022\u000e\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u001f\u001a\u00020\u00022\u0014\u0010 \u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\r2\u0006\u0010!\u001a\u00020\u0002R\u000e\u0010\u0005\u001a\u00020\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Llib/loader/DataLoader;", "Landroid/os/AsyncTask;", "", "", "()V", "boundary", "contentType", "delegate", "Llib/loader/DataLoader$Delegate;", "files", "Ljava/util/ArrayList;", "Llib/loader/FileObject;", "headers", "", "isMultipart", "", "path", "type", "urlParam", "doInBackground", "params", "", "([Ljava/lang/String;)Ljava/lang/String;", "onPostExecute", "", "result", "start", "_delegate", "param", "_type", "_files", "_boundary", "_headers", "_contentType", "Delegate", "app_debug"})
public final class DataLoader extends android.os.AsyncTask<java.lang.String, java.lang.Integer, java.lang.String> {
    private java.lang.String path;
    private java.lang.String boundary;
    private lib.loader.DataLoader.Delegate delegate;
    private java.util.Map<java.lang.String, java.lang.String> urlParam;
    private java.util.Map<java.lang.String, java.lang.String> headers;
    private java.util.ArrayList<lib.loader.FileObject> files;
    private java.lang.String type;
    private java.lang.String contentType;
    private boolean isMultipart;
    
    public final void start(@org.jetbrains.annotations.NotNull()
    lib.loader.DataLoader.Delegate _delegate, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> param, @org.jetbrains.annotations.NotNull()
    java.lang.String _type, @org.jetbrains.annotations.Nullable()
    java.util.ArrayList<lib.loader.FileObject> _files, @org.jetbrains.annotations.NotNull()
    java.lang.String _boundary, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> _headers, @org.jetbrains.annotations.NotNull()
    java.lang.String _contentType) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    protected java.lang.String doInBackground(@org.jetbrains.annotations.NotNull()
    java.lang.String... params) {
        return null;
    }
    
    @java.lang.Override()
    protected void onPostExecute(@org.jetbrains.annotations.Nullable()
    java.lang.String result) {
    }
    
    public DataLoader() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0012\u0010\u0007\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&\u00a8\u0006\b"}, d2 = {"Llib/loader/DataLoader$Delegate;", "", "onCompleted", "", "path", "", "result", "onLoadErr", "app_debug"})
    public static abstract interface Delegate {
        
        public abstract void onCompleted(@org.jetbrains.annotations.Nullable()
        java.lang.String path, @org.jetbrains.annotations.NotNull()
        java.lang.String result);
        
        public abstract void onLoadErr(@org.jetbrains.annotations.Nullable()
        java.lang.String path);
    }
}