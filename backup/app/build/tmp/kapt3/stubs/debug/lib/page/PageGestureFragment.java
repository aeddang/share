package lib.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016J\u0018\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u0014H\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Llib/page/PageGestureFragment;", "Llib/page/PageFragment;", "Llib/page/PageGestureView$Delegate;", "()V", "backgroundView", "Landroid/view/View;", "getBackgroundView", "()Landroid/view/View;", "setBackgroundView", "(Landroid/view/View;)V", "contentsView", "getContentsView", "setContentsView", "gestureView", "Llib/page/PageGestureView;", "getGestureView", "()Llib/page/PageGestureView;", "setGestureView", "(Llib/page/PageGestureView;)V", "onClose", "", "view", "onCreateAnimation", "", "onDestroyAnimation", "onDestroyView", "onMove", "pct", "", "onReturn", "willCreateAnimation", "app_debug"})
public abstract class PageGestureFragment extends lib.page.PageFragment implements lib.page.PageGestureView.Delegate {
    @org.jetbrains.annotations.Nullable()
    private lib.page.PageGestureView gestureView;
    @org.jetbrains.annotations.Nullable()
    private android.view.View contentsView;
    @org.jetbrains.annotations.Nullable()
    private android.view.View backgroundView;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    protected final lib.page.PageGestureView getGestureView() {
        return null;
    }
    
    protected final void setGestureView(@org.jetbrains.annotations.Nullable()
    lib.page.PageGestureView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected final android.view.View getContentsView() {
        return null;
    }
    
    protected final void setContentsView(@org.jetbrains.annotations.Nullable()
    android.view.View p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected final android.view.View getBackgroundView() {
        return null;
    }
    
    protected final void setBackgroundView(@org.jetbrains.annotations.Nullable()
    android.view.View p0) {
    }
    
    @java.lang.Override()
    public void willCreateAnimation() {
    }
    
    @java.lang.Override()
    public long onCreateAnimation() {
        return 0L;
    }
    
    @java.lang.Override()
    public long onDestroyAnimation() {
        return 0L;
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @java.lang.Override()
    public void onMove(@org.jetbrains.annotations.NotNull()
    lib.page.PageGestureView view, float pct) {
    }
    
    @java.lang.Override()
    public void onClose(@org.jetbrains.annotations.NotNull()
    lib.page.PageGestureView view) {
    }
    
    @java.lang.Override()
    public void onReturn(@org.jetbrains.annotations.NotNull()
    lib.page.PageGestureView view) {
    }
    
    public PageGestureFragment() {
        super();
    }
}