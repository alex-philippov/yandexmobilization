package com.qwerty.yandextranslate.model.domain.interactors;

import com.qwerty.yandextranslate.model.data.db.RealmHelper;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.model.domain.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class TranslationGetter implements TranslationGetterInteractor {
    @Inject
    Repository repository;
    @Inject
    RealmHelper realmHelper;

    private PublishSubject<Translation> translationSubject = PublishSubject.create();
    private PublishSubject<Boolean> deleteSubject = PublishSubject.create();
    private PublishSubject<Boolean> saveSubject = PublishSubject.create();
    private PublishSubject<Boolean> checkedSubject = PublishSubject.create();

    public TranslationGetter(Repository repository, RealmHelper realmHelper) {
        this.repository = repository;
        this.realmHelper = realmHelper;
    }

    private Observable<Translation> translate(String text, Language from, Language to) {
        return repository.translate(text, from.getName() + "-" + to.getName(), "ru");
    }

    @Override
    public Observable<Translation> translate(Translation translation) {
        return translate(translation.getOriginalText(), translation.getFromLang(),
                translation.getToLang())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(receivedTranslation -> {
                    receivedTranslation.setFromLang(translation.getFromLang());
                    receivedTranslation.setToLang(translation.getToLang());
                    receivedTranslation.setOriginalText(translation.getOriginalText());
                })
                .mergeWith(translationSubject);
    }

    @Override
    public PublishSubject<Translation> getTranslationSetter() {
        return translationSubject;
    }

    @Override
    public List<Translation> getHistory() {
        return repository.getHistory();
    }

    @Override
    public List<Translation> getFavorites() {
        return repository.getFavorites();
    }

    @Override
    public void saveTranslation(Translation translation) {
        repository
                .saveTranslation(translation)
                .subscribe(saveSubject::onNext, saveSubject::onError);
    }

    @Override
    public void deleteHistory() {
        repository
                .deleteHistory()
                .subscribe(deleteSubject::onNext, deleteSubject::onError);
    }

    @Override
    public void deleteFavorites() {
        repository
                .deleteFavorites()
                .subscribe(deleteSubject::onNext, deleteSubject::onError);
    }

    @Override
    public void checkChanged() {
        checkedSubject.onNext(true);
    }


    @Override
    public Observable<Boolean> getDeleteObservable() {
        return deleteSubject;
    }

    @Override
    public Observable<Boolean> getSavedObservable() {
        return saveSubject;
    }

    @Override
    public Observable<Boolean> getCheckedObservable() {
        return checkedSubject;
    }


}
