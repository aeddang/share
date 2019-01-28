package com.kakaovx.homet.component.app;

import android.app.Application;
import com.google.gson.Gson;
import com.kakaovx.homet.component.network.NetworkModule;
import com.kakaovx.homet.component.network.NetworkModule_ProvideCacheFactory;
import com.kakaovx.homet.component.network.NetworkModule_ProvideGsFactory;
import com.kakaovx.homet.component.network.NetworkModule_ProvideInterceptorFactory;
import com.kakaovx.homet.component.network.NetworkModule_ProvideOkHttpClientFactory;
import com.kakaovx.homet.component.network.NetworkModule_ProvideRetrofitFactory;
import com.kakaovx.homet.component.preference.PreferenceModule;
import com.kakaovx.homet.component.preference.PreferenceModule_ProvideSettingPreferenceFactory;
import com.kakaovx.homet.component.preference.SettingPreference;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<Gson> provideGsProvider;

  private Provider<Application> provideAppProvider;

  private Provider<Cache> provideCacheProvider;

  private Provider<Interceptor> provideInterceptorProvider;

  private Provider<OkHttpClient> provideOkHttpClientProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<SettingPreference> provideSettingPreferenceProvider;

  private DaggerAppComponent(Builder builder) {

    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideGsProvider =
        DoubleCheck.provider(NetworkModule_ProvideGsFactory.create(builder.networkModule));
    this.provideAppProvider =
        DoubleCheck.provider(AppModule_ProvideAppFactory.create(builder.appModule));
    this.provideCacheProvider =
        DoubleCheck.provider(
            NetworkModule_ProvideCacheFactory.create(builder.networkModule, provideAppProvider));
    this.provideInterceptorProvider =
        DoubleCheck.provider(NetworkModule_ProvideInterceptorFactory.create(builder.networkModule));
    this.provideOkHttpClientProvider =
        DoubleCheck.provider(
            NetworkModule_ProvideOkHttpClientFactory.create(
                builder.networkModule, provideCacheProvider, provideInterceptorProvider));
    this.provideRetrofitProvider =
        DoubleCheck.provider(
            NetworkModule_ProvideRetrofitFactory.create(
                builder.networkModule, provideGsProvider, provideOkHttpClientProvider));
    this.provideSettingPreferenceProvider =
        DoubleCheck.provider(
            PreferenceModule_ProvideSettingPreferenceFactory.create(builder.preferenceModule));
  }

  @Override
  public Retrofit retrofit() {
    return provideRetrofitProvider.get();
  }

  @Override
  public SettingPreference setting() {
    return provideSettingPreferenceProvider.get();
  }

  public static final class Builder {
    private NetworkModule networkModule;

    private AppModule appModule;

    private PreferenceModule preferenceModule;

    private Builder() {}

    public AppComponent build() {
      if (networkModule == null) {
        this.networkModule = new NetworkModule();
      }
      Preconditions.checkBuilderRequirement(appModule, AppModule.class);
      Preconditions.checkBuilderRequirement(preferenceModule, PreferenceModule.class);
      return new DaggerAppComponent(this);
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder preferenceModule(PreferenceModule preferenceModule) {
      this.preferenceModule = Preconditions.checkNotNull(preferenceModule);
      return this;
    }

    public Builder networkModule(NetworkModule networkModule) {
      this.networkModule = Preconditions.checkNotNull(networkModule);
      return this;
    }
  }
}
