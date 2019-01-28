package com.kakaovx.homet.component.network;

import java.lang.System;

/**
 * * @author Leopold
 */
@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/kakaovx/homet/component/network/RetryPolicy;", "", "()V", "Companion", "app_debug"})
public final class RetryPolicy {
    private static final int HIGH = 3;
    private static final int MEDIUM = 2;
    private static final int LOW = 1;
    public static final com.kakaovx.homet.component.network.RetryPolicy.Companion Companion = null;
    
    public RetryPolicy() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bJ\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bJ\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bJ\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/kakaovx/homet/component/network/RetryPolicy$Companion;", "", "()V", "HIGH", "", "LOW", "MEDIUM", "authorization", "Lio/reactivex/functions/BiPredicate;", "", "default", "none", "socketTimeout", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final io.reactivex.functions.BiPredicate<java.lang.Integer, java.lang.Throwable> socketTimeout() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.reactivex.functions.BiPredicate<java.lang.Integer, java.lang.Throwable> authorization() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.reactivex.functions.BiPredicate<java.lang.Integer, java.lang.Throwable> none() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}