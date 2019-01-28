package com.kakaovx.homet.component.app;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideAppFactory implements Factory<Application> {
  private final AppModule module;

  public AppModule_ProvideAppFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Application get() {
    return provideInstance(module);
  }

  public static Application provideInstance(AppModule module) {
    return proxyProvideApp(module);
  }

  public static AppModule_ProvideAppFactory create(AppModule module) {
    return new AppModule_ProvideAppFactory(module);
  }

  public static Application proxyProvideApp(AppModule instance) {
    return Preconditions.checkNotNull(
        instance.provideApp(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
