package com.qwerty.yandextranslate.common.dagger.components;

import com.qwerty.yandextranslate.common.dagger.modules.InteractorsModule;
import com.qwerty.yandextranslate.presenter.TranslatePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {InteractorsModule.class})
public interface DomainComponent {
    //void inject(TranslatePresenter presenter);
}
