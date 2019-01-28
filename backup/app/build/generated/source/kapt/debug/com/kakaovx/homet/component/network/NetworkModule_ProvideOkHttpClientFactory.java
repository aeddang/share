package com.kakaovx.homet.component.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final NetworkModule module;

  private final Provider<Cache> cacheProvider;

  private final Provider<Interceptor> interceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(
      NetworkModule module,
      Provider<Cache> cacheProvider,
      Provider<Interceptor> interceptorProvider) {
    this.module = module;
    this.cacheProvider = cacheProvider;
    this.interceptorProvider = interceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideInstance(module, cacheProvider, interceptorProvider);
  }

  public static OkHttpClient provideInstance(
      NetworkModule module,
      Provider<Cache> cacheProvider,
      Provider<Interceptor> interceptorProvider) {
    return proxyProvideOkHttpClient(module, cacheProvider.get(), interceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      NetworkModule module,
      Provider<Cache> cacheProvider,
      Provider<Interceptor> interceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(module, cacheProvider, interceptorProvider);
  }

  public static OkHttpClient proxyProvideOkHttpClient(
      NetworkModule instance, Cache cache, Interceptor interceptor) {
    return Preconditions.checkNotNull(
        instance.provideOkHttpClient(cache, interceptor),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
