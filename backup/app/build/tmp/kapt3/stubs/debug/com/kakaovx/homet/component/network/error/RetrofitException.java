package com.kakaovx.homet.component.network.error;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u001b2\u00060\u0001j\u0002`\u0002:\u0001\u001bB9\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ!\u0010\u0014\u001a\u0004\u0018\u0001H\u0015\"\u0004\b\u0000\u0010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0017\u00a2\u0006\u0002\u0010\u0018J\u0006\u0010\u0019\u001a\u00020\u001aR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001c"}, d2 = {"Lcom/kakaovx/homet/component/network/error/RetrofitException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "message", "", "response", "Lretrofit2/Response;", "kind", "Lcom/kakaovx/homet/component/network/error/ErrorKind;", "exception", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/String;Lretrofit2/Response;Lcom/kakaovx/homet/component/network/error/ErrorKind;Ljava/lang/Throwable;Lretrofit2/Retrofit;)V", "getKind", "()Lcom/kakaovx/homet/component/network/error/ErrorKind;", "getMessage", "()Ljava/lang/String;", "getRetrofit", "()Lretrofit2/Retrofit;", "getErrorBodyAs", "T", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "isTimeout", "", "Companion", "app_debug"})
public final class RetrofitException extends java.lang.RuntimeException {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String message = null;
    private final retrofit2.Response<?> response = null;
    @org.jetbrains.annotations.NotNull()
    private final com.kakaovx.homet.component.network.error.ErrorKind kind = null;
    private final java.lang.Throwable exception = null;
    @org.jetbrains.annotations.Nullable()
    private final retrofit2.Retrofit retrofit = null;
    public static final com.kakaovx.homet.component.network.error.RetrofitException.Companion Companion = null;
    
    public final boolean isTimeout() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final <T extends java.lang.Object>T getErrorBodyAs(@org.jetbrains.annotations.NotNull()
    java.lang.Class<T> type) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kakaovx.homet.component.network.error.ErrorKind getKind() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final retrofit2.Retrofit getRetrofit() {
        return null;
    }
    
    public RetrofitException(@org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    retrofit2.Response<?> response, @org.jetbrains.annotations.NotNull()
    com.kakaovx.homet.component.network.error.ErrorKind kind, @org.jetbrains.annotations.Nullable()
    java.lang.Throwable exception, @org.jetbrains.annotations.Nullable()
    retrofit2.Retrofit retrofit) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\r\u00a8\u0006\u000e"}, d2 = {"Lcom/kakaovx/homet/component/network/error/RetrofitException$Companion;", "", "()V", "httpError", "Lcom/kakaovx/homet/component/network/error/RetrofitException;", "response", "Lretrofit2/Response;", "retrofit", "Lretrofit2/Retrofit;", "networkError", "exception", "Ljava/io/IOException;", "unexpectedError", "", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.kakaovx.homet.component.network.error.RetrofitException httpError(@org.jetbrains.annotations.NotNull()
        retrofit2.Response<?> response, @org.jetbrains.annotations.Nullable()
        retrofit2.Retrofit retrofit) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kakaovx.homet.component.network.error.RetrofitException networkError(@org.jetbrains.annotations.NotNull()
        java.io.IOException exception) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.kakaovx.homet.component.network.error.RetrofitException unexpectedError(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable exception) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}