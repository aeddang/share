package com.kakaovx.homet.component.preference;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/kakaovx/homet/component/preference/PreferenceModule;", "", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "provideSettingPreference", "Lcom/kakaovx/homet/component/preference/SettingPreference;", "app_debug"})
@dagger.Module()
public final class PreferenceModule {
    private final android.app.Application app = null;
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.kakaovx.homet.component.preference.SettingPreference provideSettingPreference() {
        return null;
    }
    
    public PreferenceModule(@org.jetbrains.annotations.NotNull()
    android.app.Application app) {
        super();
    }
}