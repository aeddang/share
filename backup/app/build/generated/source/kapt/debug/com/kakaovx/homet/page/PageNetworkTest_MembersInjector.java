package com.kakaovx.homet.page;

import com.kakaovx.homet.component.network.api.GitHubApi;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PageNetworkTest_MembersInjector implements MembersInjector<PageNetworkTest> {
  private final Provider<GitHubApi> apiProvider;

  public PageNetworkTest_MembersInjector(Provider<GitHubApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  public static MembersInjector<PageNetworkTest> create(Provider<GitHubApi> apiProvider) {
    return new PageNetworkTest_MembersInjector(apiProvider);
  }

  @Override
  public void injectMembers(PageNetworkTest instance) {
    injectApi(instance, apiProvider.get());
  }

  public static void injectApi(PageNetworkTest instance, GitHubApi api) {
    instance.api = api;
  }
}
