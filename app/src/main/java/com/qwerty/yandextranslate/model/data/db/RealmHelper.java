package com.qwerty.yandextranslate.model.data.db;

import android.util.Log;

import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Helper class for working with Realm
 */
public class RealmHelper {
    /**
     * Realm instance for the UI thread
     */
    @Inject
    Realm realmUI;

    public RealmHelper(Realm realm) {
        realmUI = realm;
    }
    private PublishSubject<Boolean> deleteSubject = PublishSubject.create();
    private PublishSubject<Boolean> saveSubject = PublishSubject.create();

    /**
     * Read from the Realm on the UI thread
     * @return last response
     */
    public Language readLanguageResponseFromRealm() {
        return realmUI
                .where(Language.class)
                .findFirst();
    }

    public List<Language> readAllSupportLanguages() {
        Log.d("tag_", "realUi " + (realmUI == null));
        return realmUI
                .where(Language.class)
                .findAllSorted("fullName");
    }

    /**
     * Remove last response and write new one into realm
     */
    public void writeLanguageResponseToRealm(Language language) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Language lang = transaction
                    .where(Language.class)
                    .equalTo("name", language.getName())
                    .findFirst();
            if (lang == null) {
                lang = transaction.createObject(Language.class, language.getName());
            }
            lang.setFullName(language.getFullName());
        });
        realm.close();
    }

    public Observable<Boolean> writeTranslationToRealm(Translation translation) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(
                transaction -> {
                    Translation tr = transaction
                            .where(Translation.class)
                            .equalTo("mOriginalText", translation.getOriginalText())
                            .findFirst();
                    if (tr == null) {
                        tr = transaction.createObject(Translation.class);
                        tr.createFrom(translation, transaction);
                        tr.setHistory(true);
                    } else {
                        tr.update();
                    }
                });
        saveSubject.onNext(true);
        realm.close();
        return saveSubject;
    }

    public Observable<Boolean> clearFavorites() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                transaction -> {
                    RealmResults<Translation> results = transaction
                            .where(Translation.class)
                            .equalTo("mInFavorites", true)
                            .findAll();
                    for (Translation result : results) {
                        result.setFavorites(false);
                        if (!result.isHistory()) {
                            result.deleteFromRealm();
                        }
                    }
                },
                () -> deleteSubject.onNext(true),
                error -> deleteSubject.onError(error));
        realm.close();
        return deleteSubject;
    }

    public Observable<Boolean> clearHistory() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(
                transaction -> {
                    RealmResults<Translation> results = transaction
                            .where(Translation.class)
                            .equalTo("mInHistory", true)
                            .findAll();
                    for (Translation result : results) {
                        result.setHistory(false);
                        if (!result.isFavorites()) {
                            result.deleteFromRealm();
                        }
                    }
                }, () -> deleteSubject.onNext(true),
                error -> deleteSubject.onError(error));
        realm.close();
        return deleteSubject;
    }

    public List<Translation> getFavorites() {
        return realmUI
                .where(Translation.class)
                .equalTo("mInFavorites", true)
                .findAll();
    }

    public List<Translation> getHistory() {
        return realmUI
                .where(Translation.class)
                .equalTo("mInHistory", true)
                .findAll();
    }

    public void closeRealmUi() {
        realmUI.close();
        Log.d("tag_", "close()");
    }

    public Realm getRealmUI() {
        return realmUI;
    }

}
