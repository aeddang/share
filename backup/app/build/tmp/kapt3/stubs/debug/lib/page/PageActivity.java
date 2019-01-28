package lib.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u0016J\u0014\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0001\u0010\u00012\u0006\u0010\u001c\u001a\u0002H\u0001H\u0014\u00a2\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0001\u0010\u00012\u0006\u0010\u001c\u001a\u0002H\u0001H\u0014\u00a2\u0006\u0002\u0010\u001dJ\b\u0010\u001f\u001a\u00020 H\u0016J\u0013\u0010!\u001a\u00020 2\u0006\u0010\u001c\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020\u0007H\u0016J\u0013\u0010%\u001a\u00020 2\u0006\u0010\u001c\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\"J\u001b\u0010&\u001a\u00020 2\u0006\u0010\u001c\u001a\u00028\u00002\u0006\u0010\'\u001a\u00020(\u00a2\u0006\u0002\u0010)J\b\u0010*\u001a\u00020 H\u0014R(\u0010\b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007@TX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006+"}, d2 = {"Llib/page/PageActivity;", "T", "Landroid/support/v7/app/AppCompatActivity;", "Llib/page/PagePresenter$View;", "Llib/page/PageFragment$Delegate;", "()V", "<set-?>", "Llib/page/PageFragment;", "currentPage", "getCurrentPage", "()Llib/page/PageFragment;", "setCurrentPage", "(Llib/page/PageFragment;)V", "pageArea", "Landroid/view/ViewGroup;", "getPageArea", "()Landroid/view/ViewGroup;", "setPageArea", "(Landroid/view/ViewGroup;)V", "pagePresenter", "Llib/page/PagePresenter;", "getPagePresenter", "()Llib/page/PagePresenter;", "getCurentFragment", "getPageAreaSize", "Lkotlin/Pair;", "", "getPageByID", "id", "(Ljava/lang/Object;)Llib/page/PageFragment;", "getPopupByID", "onBackPressed", "", "onClosePopup", "(Ljava/lang/Object;)V", "onCreateAnimation", "v", "onOpenPopup", "onPageChange", "isBack", "", "(Ljava/lang/Object;Z)V", "onStart", "app_debug"})
public abstract class PageActivity<T extends java.lang.Object> extends android.support.v7.app.AppCompatActivity implements lib.page.PagePresenter.View<T>, lib.page.PageFragment.Delegate {
    @org.jetbrains.annotations.NotNull()
    private final lib.page.PagePresenter<T> pagePresenter = null;
    @org.jetbrains.annotations.Nullable()
    private lib.page.PageFragment currentPage;
    @org.jetbrains.annotations.Nullable()
    private android.view.ViewGroup pageArea;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public lib.page.PagePresenter<T> getPagePresenter() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public lib.page.PageFragment getCurrentPage() {
        return null;
    }
    
    protected void setCurrentPage(@org.jetbrains.annotations.Nullable()
    lib.page.PageFragment p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected final android.view.ViewGroup getPageArea() {
        return null;
    }
    
    protected final void setPageArea(@org.jetbrains.annotations.Nullable()
    android.view.ViewGroup p0) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public lib.page.PageFragment getCurentFragment() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlin.Pair<java.lang.Float, java.lang.Float> getPageAreaSize() {
        return null;
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected <T extends java.lang.Object>lib.page.PageFragment getPageByID(T id) {
        return null;
    }
    
    @java.lang.Override()
    public final void onPageChange(T id, boolean isBack) {
    }
    
    @java.lang.Override()
    public void onCreateAnimation(@org.jetbrains.annotations.NotNull()
    lib.page.PageFragment v) {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected <T extends java.lang.Object>lib.page.PageFragment getPopupByID(T id) {
        return null;
    }
    
    @java.lang.Override()
    public final void onOpenPopup(T id) {
    }
    
    @java.lang.Override()
    public final void onClosePopup(T id) {
    }
    
    public PageActivity() {
        super();
    }
}