package com.qwerty.yandextranslate.common.dagger.modules;

import com.qwerty.yandextranslate.model.data.db.RealmHelper;
import com.qwerty.yandextranslate.model.domain.interactors.SupportLangsGetterInteractor;
import com.qwerty.yandextranslate.model.domain.interactors.SupportLangsGetter;
import com.qwerty.yandextranslate.model.domain.Repository;
import com.qwerty.yandextranslate.model.domain.interactors.TranslationGetter;
import com.qwerty.yandextranslate.model.domain.interactors.TranslationGetterInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {
    @Provides
    @Singleton
    SupportLangsGetterInteractor provideLangsUiRu(Repository repository) {
        return new SupportLangsGetter(repository);
    }

    @Provides
    @Singleton
    TranslationGetterInteractor provideInteractor(Repository repository, RealmHelper realmHelper) {
        return new TranslationGetter(repository, realmHelper);
    }
}
