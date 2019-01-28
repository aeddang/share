package com.kakaovx.homet.component.network;

import android.view.View;
import com.kakaovx.homet.component.app.AppComponent;
import com.kakaovx.homet.component.network.api.GitHubApi;
import com.kakaovx.homet.page.PageNetworkTest;
import com.kakaovx.homet.page.PageNetworkTest_MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerApiComponent implements ApiComponent {
  private com_kakaovx_homet_component_app_AppComponent_retrofit retrofitProvider;

  private Provider<GitHubApi> provideGitHubApiProvider;

  private DaggerApiComponent(Builder builder) {

    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.retrofitProvider =
        new com_kakaovx_homet_component_app_AppComponent_retrofit(builder.appComponent);
    this.provideGitHubApiProvider =
        DoubleCheck.provider(
            ApiModule_ProvideGitHubApiFactory.create(builder.apiModule, retrofitProvider));
  }

  @Override
  public void inject(View context) {}

  @Override
  public void inject(PageNetworkTest context) {
    injectPageNetworkTest(context);
  }

  private PageNetworkTest injectPageNetworkTest(PageNetworkTest instance) {
    PageNetworkTest_MembersInjector.injectApi(instance, provideGitHubApiProvider.get());
    return instance;
  }

  public static final class Builder {
    private ApiModule apiModule;

    private AppComponent appComponent;

    private Builder() {}

    public ApiComponent build() {
      if (apiModule == null) {
        this.apiModule = new ApiModule();
      }
      Preconditions.checkBuilderRequirement(appComponent, AppComponent.class);
      return new DaggerApiComponent(this);
    }

    public Builder apiModule(ApiModule apiModule) {
      this.apiModule = Preconditions.checkNotNull(apiModule);
      return this;
    }

    public Builder appComponent(AppComponent appComponent) {
      this.appComponent = Preconditions.checkNotNull(appComponent);
      return this;
    }
  }

  private static class com_kakaovx_homet_component_app_AppComponent_retrofit
      implements Provider<Retrofit> {
    private final AppComponent appComponent;

    com_kakaovx_homet_component_app_AppComponent_retrofit(AppComponent appComponent) {
      this.appComponent = appComponent;
    }

    @Override
    public Retrofit get() {
      return Preconditions.checkNotNull(
          appComponent.retrofit(), "Cannot return null from a non-@Nullable component method");
    }
  }
}
