package lib.datastructure;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\u0012J\u0015\u0010\u0013\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0014\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0015J\u0015\u0010\u0013\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0016\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0017J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0002J\u0013\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u0019J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003H\u0086\u0002J\u000e\u0010\u001e\u001a\u00028\u0001H\u0096\u0002\u00a2\u0006\u0002\u0010\u001fJ\r\u0010 \u001a\u0004\u0018\u00018\u0001\u00a2\u0006\u0002\u0010\u001fJ\r\u0010!\u001a\u0004\u0018\u00018\u0001\u00a2\u0006\u0002\u0010\u001fJ\u001b\u0010\"\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u0001\u00a2\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00028\u0000\u00a2\u0006\u0002\u0010&J\u000e\u0010%\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068F@BX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\'"}, d2 = {"Llib/datastructure/IndexMap;", "K", "V", "Ljava/util/Iterator;", "()V", "currentIndex", "", "index", "Ljava/util/ArrayList;", "map", "Ljava/util/HashMap;", "<set-?>", "size", "getSize", "()I", "setSize", "(I)V", "clear", "", "get", "key", "(Ljava/lang/Object;)Ljava/lang/Object;", "idx", "(I)Ljava/lang/Object;", "hasNext", "", "indexOf", "(Ljava/lang/Object;)I", "isEmpty", "iterator", "next", "()Ljava/lang/Object;", "poll", "pop", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)I", "remove", "(Ljava/lang/Object;)V", "app_debug"})
public final class IndexMap<K extends java.lang.Object, V extends java.lang.Object> implements java.util.Iterator<V> {
    private final java.util.HashMap<K, V> map = null;
    private final java.util.ArrayList<K> index = null;
    private int currentIndex;
    private int size;
    
    public final int getSize() {
        return 0;
    }
    
    private final void setSize(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Iterator<V> iterator() {
        return null;
    }
    
    public final int indexOf(K key) {
        return 0;
    }
    
    @java.lang.Override()
    public boolean hasNext() {
        return false;
    }
    
    @java.lang.Override()
    public V next() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final V get(K key) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final V get(int idx) {
        return null;
    }
    
    public final void clear() {
    }
    
    public final void remove(int idx) {
    }
    
    public final void remove(K key) {
    }
    
    public final boolean isEmpty() {
        return false;
    }
    
    public final int put(K key, V value) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final V poll() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final V pop() {
        return null;
    }
    
    public IndexMap() {
        super();
    }
}