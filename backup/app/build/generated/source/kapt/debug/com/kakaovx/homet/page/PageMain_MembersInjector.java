package com.kakaovx.homet.page;

import com.kakaovx.homet.component.network.api.GitHubApi;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PageMain_MembersInjector implements MembersInjector<PageMain> {
  private final Provider<GitHubApi> apiProvider;

  public PageMain_MembersInjector(Provider<GitHubApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  public static MembersInjector<PageMain> create(Provider<GitHubApi> apiProvider) {
    return new PageMain_MembersInjector(apiProvider);
  }

  @Override
  public void injectMembers(PageMain instance) {
    injectApi(instance, apiProvider.get());
  }

  public static void injectApi(PageMain instance, GitHubApi api) {
    instance.api = api;
  }
}
