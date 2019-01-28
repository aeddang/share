package com.kakaovx.homet.component.network;

import com.kakaovx.homet.component.network.api.GitHubApi;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ApiModule_ProvideGitHubApiFactory implements Factory<GitHubApi> {
  private final ApiModule module;

  private final Provider<Retrofit> retrofitProvider;

  public ApiModule_ProvideGitHubApiFactory(ApiModule module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GitHubApi get() {
    return provideInstance(module, retrofitProvider);
  }

  public static GitHubApi provideInstance(ApiModule module, Provider<Retrofit> retrofitProvider) {
    return proxyProvideGitHubApi(module, retrofitProvider.get());
  }

  public static ApiModule_ProvideGitHubApiFactory create(
      ApiModule module, Provider<Retrofit> retrofitProvider) {
    return new ApiModule_ProvideGitHubApiFactory(module, retrofitProvider);
  }

  public static GitHubApi proxyProvideGitHubApi(ApiModule instance, Retrofit retrofit) {
    return Preconditions.checkNotNull(
        instance.provideGitHubApi(retrofit),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
