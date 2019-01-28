package com.kakaovx.homet.component.app;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/kakaovx/homet/component/app/AppComponent;", "", "retrofit", "Lretrofit2/Retrofit;", "setting", "Lcom/kakaovx/homet/component/preference/SettingPreference;", "app_debug"})
@dagger.Component(modules = {com.kakaovx.homet.component.app.AppModule.class, com.kakaovx.homet.component.preference.PreferenceModule.class, com.kakaovx.homet.component.network.NetworkModule.class})
@javax.inject.Singleton()
public abstract interface AppComponent {
    
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Retrofit retrofit();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.kakaovx.homet.component.preference.SettingPreference setting();
}