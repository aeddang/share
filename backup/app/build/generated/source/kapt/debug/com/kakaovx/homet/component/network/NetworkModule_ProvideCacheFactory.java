package com.kakaovx.homet.component.network;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideCacheFactory implements Factory<Cache> {
  private final NetworkModule module;

  private final Provider<Application> applicationProvider;

  public NetworkModule_ProvideCacheFactory(
      NetworkModule module, Provider<Application> applicationProvider) {
    this.module = module;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Cache get() {
    return provideInstance(module, applicationProvider);
  }

  public static Cache provideInstance(
      NetworkModule module, Provider<Application> applicationProvider) {
    return proxyProvideCache(module, applicationProvider.get());
  }

  public static NetworkModule_ProvideCacheFactory create(
      NetworkModule module, Provider<Application> applicationProvider) {
    return new NetworkModule_ProvideCacheFactory(module, applicationProvider);
  }

  public static Cache proxyProvideCache(NetworkModule instance, Application application) {
    return Preconditions.checkNotNull(
        instance.provideCache(application),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
