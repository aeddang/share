package com.kakaovx.homet.component.page;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H$J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0017\u00a8\u0006\n"}, d2 = {"Lcom/kakaovx/homet/component/page/InjectablePageFragment;", "Llib/page/PageFragment;", "()V", "inject", "", "fragment", "Landroid/support/v4/app/Fragment;", "onAttach", "context", "Landroid/content/Context;", "app_debug"})
public abstract class InjectablePageFragment extends lib.page.PageFragment {
    private java.util.HashMap _$_findViewCache;
    
    protected abstract void inject(@org.jetbrains.annotations.NotNull()
    android.support.v4.app.Fragment fragment);
    
    @android.support.annotation.CallSuper()
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
    }
    
    public InjectablePageFragment() {
        super();
    }
}