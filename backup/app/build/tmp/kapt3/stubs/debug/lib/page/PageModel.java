package lib.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0016\u00a2\u0006\u0002\u0010\u0017J\u0015\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\tJ\b\u0010\u0019\u001a\u00020\u0013H\u0016J\u000f\u0010\u001a\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u001b\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0002\u0010\u0007J\u0015\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001d"}, d2 = {"Llib/page/PageModel;", "T", "Llib/page/PagePresenter$Model;", "()V", "currentHistoryStack", "", "getCurrentHistoryStack", "()Ljava/lang/Object;", "setCurrentHistoryStack", "(Ljava/lang/Object;)V", "historys", "Ljava/util/Stack;", "getHistorys", "()Ljava/util/Stack;", "popups", "Ljava/util/ArrayList;", "getPopups", "()Ljava/util/ArrayList;", "addHistory", "", "id", "isHistory", "", "(Ljava/lang/Object;Z)V", "addPopup", "clearAllHistory", "getHistory", "getPopup", "removePopup", "app_debug"})
public final class PageModel<T extends java.lang.Object> implements lib.page.PagePresenter.Model<T> {
    @org.jetbrains.annotations.Nullable()
    private java.lang.Object currentHistoryStack;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Stack<java.lang.Object> historys = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<java.lang.Object> popups = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentHistoryStack() {
        return null;
    }
    
    public final void setCurrentHistoryStack(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Stack<java.lang.Object> getHistorys() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<java.lang.Object> getPopups() {
        return null;
    }
    
    @java.lang.Override()
    public void addHistory(T id, boolean isHistory) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public T getHistory() {
        return null;
    }
    
    @java.lang.Override()
    public void clearAllHistory() {
    }
    
    @java.lang.Override()
    public void removePopup(T id) {
    }
    
    @java.lang.Override()
    public void addPopup(T id) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public T getPopup() {
        return null;
    }
    
    public PageModel() {
        super();
    }
}