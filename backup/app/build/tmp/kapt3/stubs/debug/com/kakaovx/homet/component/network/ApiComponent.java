package com.kakaovx.homet.component.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/kakaovx/homet/component/network/ApiComponent;", "", "inject", "", "context", "Landroid/view/View;", "Lcom/kakaovx/homet/page/PageNetworkTest;", "app_debug"})
@dagger.Component(dependencies = {com.kakaovx.homet.component.app.AppComponent.class}, modules = {com.kakaovx.homet.component.network.ApiModule.class})
@com.kakaovx.homet.component.annotation.PageScope()
public abstract interface ApiComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    android.view.View context);
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    com.kakaovx.homet.page.PageNetworkTest context);
}