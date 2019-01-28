package com.kakaovx.homet.component.network;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideGsFactory implements Factory<Gson> {
  private final NetworkModule module;

  public NetworkModule_ProvideGsFactory(NetworkModule module) {
    this.module = module;
  }

  @Override
  public Gson get() {
    return provideInstance(module);
  }

  public static Gson provideInstance(NetworkModule module) {
    return proxyProvideGs(module);
  }

  public static NetworkModule_ProvideGsFactory create(NetworkModule module) {
    return new NetworkModule_ProvideGsFactory(module);
  }

  public static Gson proxyProvideGs(NetworkModule instance) {
    return Preconditions.checkNotNull(
        instance.provideGs(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
