package com.qwerty.yandextranslate.model.domain.interactors;

import com.qwerty.yandextranslate.App;
import com.qwerty.yandextranslate.common.utils.Mapper;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SupportLangsGetter implements SupportLangsGetterInteractor {

    @Inject
    Repository mRepository;
    @Inject
    Mapper mapper;

    public SupportLangsGetter(Repository repository) {
        App.getDataComponent().inject(this);
        this.mRepository = repository;
    }

    @Override
    public Observable<List<Language>> getLanguages() {
        return mRepository.getSupportLanguages("ru");
    }
}
