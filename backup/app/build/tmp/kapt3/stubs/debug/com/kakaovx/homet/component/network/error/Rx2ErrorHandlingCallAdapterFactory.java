package com.kakaovx.homet.component.network.error;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002JB\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0002\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/kakaovx/homet/component/network/error/Rx2ErrorHandlingCallAdapterFactory;", "Lretrofit2/CallAdapter$Factory;", "()V", "original", "Lretrofit2/adapter/rxjava2/RxJava2CallAdapterFactory;", "get", "Lretrofit2/CallAdapter;", "Lio/reactivex/Observable;", "", "returnType", "Ljava/lang/reflect/Type;", "annotations", "", "", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/CallAdapter;", "Companion", "app_debug"})
public final class Rx2ErrorHandlingCallAdapterFactory extends retrofit2.CallAdapter.Factory {
    private final retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory original = null;
    public static final com.kakaovx.homet.component.network.error.Rx2ErrorHandlingCallAdapterFactory.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    @java.lang.Override()
    public retrofit2.CallAdapter<io.reactivex.Observable<java.lang.Object>, java.lang.Object> get(@org.jetbrains.annotations.NotNull()
    java.lang.reflect.Type returnType, @org.jetbrains.annotations.NotNull()
    java.lang.annotation.Annotation[] annotations, @org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    public Rx2ErrorHandlingCallAdapterFactory() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/kakaovx/homet/component/network/error/Rx2ErrorHandlingCallAdapterFactory$Companion;", "", "()V", "create", "Lretrofit2/CallAdapter$Factory;", "RxCallAdapterWrapper", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final retrofit2.CallAdapter.Factory create() {
            return null;
        }
        
        private Companion() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00030\u0001B+\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u001a\u0010\u0006\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\n\u001a\u00020\u00032\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\u0006\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/kakaovx/homet/component/network/error/Rx2ErrorHandlingCallAdapterFactory$Companion$RxCallAdapterWrapper;", "Lretrofit2/CallAdapter;", "Lio/reactivex/Observable;", "", "retrofit", "Lretrofit2/Retrofit;", "wrapped", "(Lretrofit2/Retrofit;Lretrofit2/CallAdapter;)V", "getRetrofit", "()Lretrofit2/Retrofit;", "adapt", "call", "Lretrofit2/Call;", "asRetrofitException", "Lcom/kakaovx/homet/component/network/error/RetrofitException;", "throwable", "", "responseType", "Ljava/lang/reflect/Type;", "app_debug"})
        public static final class RxCallAdapterWrapper implements retrofit2.CallAdapter<io.reactivex.Observable<java.lang.Object>, java.lang.Object> {
            @org.jetbrains.annotations.Nullable()
            private final retrofit2.Retrofit retrofit = null;
            private final retrofit2.CallAdapter<io.reactivex.Observable<java.lang.Object>, java.lang.Object> wrapped = null;
            
            @org.jetbrains.annotations.NotNull()
            @java.lang.Override()
            public java.lang.Object adapt(@org.jetbrains.annotations.NotNull()
            retrofit2.Call<io.reactivex.Observable<java.lang.Object>> call) {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable()
            @java.lang.Override()
            public java.lang.reflect.Type responseType() {
                return null;
            }
            
            private final com.kakaovx.homet.component.network.error.RetrofitException asRetrofitException(java.lang.Throwable throwable) {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable()
            public final retrofit2.Retrofit getRetrofit() {
                return null;
            }
            
            public RxCallAdapterWrapper(@org.jetbrains.annotations.Nullable()
            retrofit2.Retrofit retrofit, @org.jetbrains.annotations.Nullable()
            retrofit2.CallAdapter<io.reactivex.Observable<java.lang.Object>, java.lang.Object> wrapped) {
                super();
            }
        }
    }
}