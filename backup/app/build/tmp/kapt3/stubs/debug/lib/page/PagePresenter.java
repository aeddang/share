package lib.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\b\u0016\u0018\u0000 \u0019*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0003\u0019\u001a\u001bB!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\u0019\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u000f\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0010J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\rJ\u0019\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u000f\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0010J-\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u000f\u001a\u00028\u00002\b\b\u0002\u0010\u0016\u001a\u00020\u00122\b\b\u0002\u0010\u0017\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0018R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Llib/page/PagePresenter;", "T", "", "view", "Llib/page/PagePresenter$View;", "model", "Llib/page/PagePresenter$Model;", "(Llib/page/PagePresenter$View;Llib/page/PagePresenter$Model;)V", "getModel", "()Llib/page/PagePresenter$Model;", "getView", "()Llib/page/PagePresenter$View;", "closeMenu", "", "closePopup", "id", "(Ljava/lang/Object;)Llib/page/PagePresenter;", "onBack", "", "openMenu", "openPopup", "pageChange", "isHistory", "isBack", "(Ljava/lang/Object;ZZ)Llib/page/PagePresenter;", "Companion", "Model", "View", "app_debug"})
public class PagePresenter<T extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull()
    private final lib.page.PagePresenter.View<T> view = null;
    @org.jetbrains.annotations.NotNull()
    private final lib.page.PagePresenter.Model<T> model = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "Page";
    private static java.lang.Object currentInstence;
    public static final lib.page.PagePresenter.Companion Companion = null;
    
    public final void openMenu() {
    }
    
    public final void closeMenu() {
    }
    
    public final boolean onBack() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PagePresenter<T> closePopup(T id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PagePresenter<T> openPopup(T id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PagePresenter<T> pageChange(T id, boolean isHistory, boolean isBack) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PagePresenter.View<T> getView() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PagePresenter.Model<T> getModel() {
        return null;
    }
    
    public PagePresenter(@org.jetbrains.annotations.NotNull()
    lib.page.PagePresenter.View<T> view, @org.jetbrains.annotations.NotNull()
    lib.page.PagePresenter.Model<T> model) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u0006J\u001d\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u00012\u0006\u0010\t\u001a\u00020\nH&\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Llib/page/PagePresenter$View;", "T", "", "onClosePopup", "", "id", "(Ljava/lang/Object;)V", "onOpenPopup", "onPageChange", "isBack", "", "(Ljava/lang/Object;Z)V", "app_debug"})
    public static abstract interface View<T extends java.lang.Object> {
        
        public abstract void onPageChange(T id, boolean isBack);
        
        public abstract void onOpenPopup(T id);
        
        public abstract void onClosePopup(T id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\bf\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u00012\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\u0004H&J\u000f\u0010\f\u001a\u0004\u0018\u00018\u0001H&\u00a2\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\u0004\u0018\u00018\u0001H&\u00a2\u0006\u0002\u0010\rJ\u0015\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u0010"}, d2 = {"Llib/page/PagePresenter$Model;", "T", "", "addHistory", "", "id", "isHistory", "", "(Ljava/lang/Object;Z)V", "addPopup", "(Ljava/lang/Object;)V", "clearAllHistory", "getHistory", "()Ljava/lang/Object;", "getPopup", "removePopup", "app_debug"})
    public static abstract interface Model<T extends java.lang.Object> {
        
        public abstract void addHistory(T id, boolean isHistory);
        
        @org.jetbrains.annotations.Nullable()
        public abstract T getHistory();
        
        public abstract void clearAllHistory();
        
        public abstract void removePopup(T id);
        
        public abstract void addPopup(T id);
        
        @org.jetbrains.annotations.Nullable()
        public abstract T getPopup();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\b\u001a\n\u0012\u0004\u0012\u0002H\n\u0018\u00010\t\"\u0004\b\u0001\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0080D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Llib/page/PagePresenter$Companion;", "", "()V", "TAG", "", "getTAG$app_debug", "()Ljava/lang/String;", "currentInstence", "getInstence", "Llib/page/PagePresenter;", "T", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTAG$app_debug() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final <T extends java.lang.Object>lib.page.PagePresenter<T> getInstence() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}