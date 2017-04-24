package com.qwerty.yandextranslate.model.domain.interactors;

import com.qwerty.yandextranslate.model.domain.Entities.Language;

import java.util.List;

import io.reactivex.Observable;

public interface SupportLangsGetterInteractor {
    Observable<List<Language>> getLanguages();
}
