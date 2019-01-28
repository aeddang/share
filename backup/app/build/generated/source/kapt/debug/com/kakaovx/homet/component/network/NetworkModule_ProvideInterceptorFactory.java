package com.kakaovx.homet.component.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.Interceptor;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideInterceptorFactory implements Factory<Interceptor> {
  private final NetworkModule module;

  public NetworkModule_ProvideInterceptorFactory(NetworkModule module) {
    this.module = module;
  }

  @Override
  public Interceptor get() {
    return provideInstance(module);
  }

  public static Interceptor provideInstance(NetworkModule module) {
    return proxyProvideInterceptor(module);
  }

  public static NetworkModule_ProvideInterceptorFactory create(NetworkModule module) {
    return new NetworkModule_ProvideInterceptorFactory(module);
  }

  public static Interceptor proxyProvideInterceptor(NetworkModule instance) {
    return Preconditions.checkNotNull(
        instance.provideInterceptor(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
