package com.qwerty.yandextranslate.model.domain.interactors;

import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public interface TranslationGetterInteractor {
    Observable<Translation> translate(Translation translation);
    PublishSubject<Translation> getTranslationSetter();
    List<Translation> getHistory();
    List<Translation> getFavorites();
    void saveTranslation(Translation translation);
    void deleteHistory();
    void deleteFavorites();
    void checkChanged();
    Observable<Boolean> getDeleteObservable();
    Observable<Boolean> getSavedObservable();
    Observable<Boolean> getCheckedObservable();

}
