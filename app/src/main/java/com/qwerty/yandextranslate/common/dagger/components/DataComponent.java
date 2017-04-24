package com.qwerty.yandextranslate.common.dagger.components;

import com.qwerty.yandextranslate.common.dagger.modules.AppModule;
import com.qwerty.yandextranslate.common.dagger.modules.InteractorsModule;
import com.qwerty.yandextranslate.common.dagger.modules.RepositoriesModule;
import com.qwerty.yandextranslate.model.data.network.RetrofitRepository;
import com.qwerty.yandextranslate.model.domain.interactors.SupportLangsGetter;
import com.qwerty.yandextranslate.presenter.FavoritesListPresenter;
import com.qwerty.yandextranslate.presenter.DictionaryPresenter;
import com.qwerty.yandextranslate.presenter.HistoryListPresenter;
import com.qwerty.yandextranslate.presenter.TranslatePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoriesModule.class, InteractorsModule.class, AppModule.class})
public interface DataComponent {
    void inject(RetrofitRepository repository);
    void inject(SupportLangsGetter langsUseCase);
    void inject(TranslatePresenter presenter);
    void inject(FavoritesListPresenter presenter);
    void inject(DictionaryPresenter presenter);
    void inject(HistoryListPresenter presenter);
}
