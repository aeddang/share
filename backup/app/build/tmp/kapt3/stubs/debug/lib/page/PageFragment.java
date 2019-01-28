package lib.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001:\u0002;<B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010(\u001a\u00020)H\u0014J\b\u0010*\u001a\u00020)H\u0014J\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020\rH\u0016J&\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u0001032\b\u00104\u001a\u0004\u0018\u000105H\u0017J\b\u00106\u001a\u00020\rH\u0016J\b\u00107\u001a\u00020)H\u0017J\u001a\u00108\u001a\u00020)2\u0006\u00109\u001a\u00020/2\b\u00104\u001a\u0004\u0018\u000105H\u0017J\b\u0010:\u001a\u00020)H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R(\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010\'\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2 = {"Llib/page/PageFragment;", "Landroid/support/v4/app/Fragment;", "()V", "animationCreateRunnable", "Ljava/lang/Runnable;", "getAnimationCreateRunnable", "()Ljava/lang/Runnable;", "setAnimationCreateRunnable", "(Ljava/lang/Runnable;)V", "animationDestroyRunnable", "getAnimationDestroyRunnable", "setAnimationDestroyRunnable", "animationDuration", "", "getAnimationDuration", "()J", "setAnimationDuration", "(J)V", "animationHandler", "Landroid/os/Handler;", "delegate", "Llib/page/PageFragment$Delegate;", "getDelegate$app_debug", "()Llib/page/PageFragment$Delegate;", "setDelegate$app_debug", "(Llib/page/PageFragment$Delegate;)V", "<set-?>", "", "pageID", "getPageID", "()Ljava/lang/Object;", "setPageID$app_debug", "(Ljava/lang/Object;)V", "pageType", "Llib/page/PageFragment$PageType;", "getPageType$app_debug", "()Llib/page/PageFragment$PageType;", "setPageType$app_debug", "(Llib/page/PageFragment$PageType;)V", "viewCreateRunnable", "didCreateAnimation", "", "didDestroyAnimation", "onBack", "", "onCreateAnimation", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyAnimation", "onDestroyView", "onViewCreated", "view", "willCreateAnimation", "Delegate", "PageType", "app_debug"})
public abstract class PageFragment extends android.support.v4.app.Fragment {
    private long animationDuration;
    private android.os.Handler animationHandler;
    private java.lang.Runnable viewCreateRunnable;
    @org.jetbrains.annotations.NotNull()
    private java.lang.Runnable animationCreateRunnable;
    @org.jetbrains.annotations.NotNull()
    private java.lang.Runnable animationDestroyRunnable;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Object pageID;
    @org.jetbrains.annotations.Nullable()
    private lib.page.PageFragment.Delegate delegate;
    @org.jetbrains.annotations.NotNull()
    private lib.page.PageFragment.PageType pageType;
    private java.util.HashMap _$_findViewCache;
    
    protected final long getAnimationDuration() {
        return 0L;
    }
    
    protected final void setAnimationDuration(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final java.lang.Runnable getAnimationCreateRunnable() {
        return null;
    }
    
    protected final void setAnimationCreateRunnable(@org.jetbrains.annotations.NotNull()
    java.lang.Runnable p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final java.lang.Runnable getAnimationDestroyRunnable() {
        return null;
    }
    
    protected final void setAnimationDestroyRunnable(@org.jetbrains.annotations.NotNull()
    java.lang.Runnable p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPageID() {
        return null;
    }
    
    public final void setPageID$app_debug(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final lib.page.PageFragment.Delegate getDelegate$app_debug() {
        return null;
    }
    
    public final void setDelegate$app_debug(@org.jetbrains.annotations.Nullable()
    lib.page.PageFragment.Delegate p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final lib.page.PageFragment.PageType getPageType$app_debug() {
        return null;
    }
    
    public final void setPageType$app_debug(@org.jetbrains.annotations.NotNull()
    lib.page.PageFragment.PageType p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @android.support.annotation.CallSuper()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @android.support.annotation.CallSuper()
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public void willCreateAnimation() {
    }
    
    public long onCreateAnimation() {
        return 0L;
    }
    
    protected void didCreateAnimation() {
    }
    
    public long onDestroyAnimation() {
        return 0L;
    }
    
    protected void didDestroyAnimation() {
    }
    
    @android.support.annotation.CallSuper()
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    public boolean onBack() {
        return false;
    }
    
    public PageFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Llib/page/PageFragment$PageType;", "", "(Ljava/lang/String;I)V", "DEFAULT", "BACK", "POPUP", "app_debug"})
    public static enum PageType {
        /*public static final*/ DEFAULT /* = new DEFAULT() */,
        /*public static final*/ BACK /* = new BACK() */,
        /*public static final*/ POPUP /* = new POPUP() */;
        
        PageType() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Llib/page/PageFragment$Delegate;", "", "onCreateAnimation", "", "v", "Llib/page/PageFragment;", "app_debug"})
    public static abstract interface Delegate {
        
        public abstract void onCreateAnimation(@org.jetbrains.annotations.NotNull()
        lib.page.PageFragment v);
    }
}