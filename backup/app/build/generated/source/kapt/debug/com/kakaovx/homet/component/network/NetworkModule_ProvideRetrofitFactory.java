package com.kakaovx.homet.component.network;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final NetworkModule module;

  private final Provider<Gson> gsProvider;

  private final Provider<OkHttpClient> clientProvider;

  public NetworkModule_ProvideRetrofitFactory(
      NetworkModule module, Provider<Gson> gsProvider, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.gsProvider = gsProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public Retrofit get() {
    return provideInstance(module, gsProvider, clientProvider);
  }

  public static Retrofit provideInstance(
      NetworkModule module, Provider<Gson> gsProvider, Provider<OkHttpClient> clientProvider) {
    return proxyProvideRetrofit(module, gsProvider.get(), clientProvider.get());
  }

  public static NetworkModule_ProvideRetrofitFactory create(
      NetworkModule module, Provider<Gson> gsProvider, Provider<OkHttpClient> clientProvider) {
    return new NetworkModule_ProvideRetrofitFactory(module, gsProvider, clientProvider);
  }

  public static Retrofit proxyProvideRetrofit(
      NetworkModule instance, Gson gs, OkHttpClient client) {
    return Preconditions.checkNotNull(
        instance.provideRetrofit(gs, client),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
