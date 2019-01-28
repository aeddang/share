package com.kakaovx.homet.component.preference;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PreferenceModule_ProvideSettingPreferenceFactory
    implements Factory<SettingPreference> {
  private final PreferenceModule module;

  public PreferenceModule_ProvideSettingPreferenceFactory(PreferenceModule module) {
    this.module = module;
  }

  @Override
  public SettingPreference get() {
    return provideInstance(module);
  }

  public static SettingPreference provideInstance(PreferenceModule module) {
    return proxyProvideSettingPreference(module);
  }

  public static PreferenceModule_ProvideSettingPreferenceFactory create(PreferenceModule module) {
    return new PreferenceModule_ProvideSettingPreferenceFactory(module);
  }

  public static SettingPreference proxyProvideSettingPreference(PreferenceModule instance) {
    return Preconditions.checkNotNull(
        instance.provideSettingPreference(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
