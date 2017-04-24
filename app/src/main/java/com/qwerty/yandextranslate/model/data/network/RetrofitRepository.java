package com.qwerty.yandextranslate.model.data.network;

import android.util.Log;

import com.qwerty.yandextranslate.App;
import com.qwerty.yandextranslate.common.utils.Data;
import com.qwerty.yandextranslate.common.utils.Mapper;
import com.qwerty.yandextranslate.model.data.db.RealmHelper;
import com.qwerty.yandextranslate.model.data.network.api.DictionaryApi;
import com.qwerty.yandextranslate.model.data.network.api.TranslateApi;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.DictionaryResponse;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.model.domain.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RetrofitRepository implements Repository {
    @Inject
    TranslateApi translateApi;
    @Inject
    DictionaryApi dictionaryApi;
    @Inject
    Mapper mMapper;
    @Inject
    RealmHelper realmHelper;

    public RetrofitRepository(TranslateApi api, Mapper mapper) {
        App.getDataComponent().inject(this);
        translateApi = api;
        mMapper = mapper;
    }

    /**
     * If cache empty
     *      REST Call;
     *      write to Realm;
     *      read from Realm;
     *      Display;
     * else
     *      REST Call;
     *      read cached form Realm;
     *      Display cached;
     *      get recent langs from call;
     *      update cache;
     *      Display recent;
     * @param ui user interface language
     * @return Observable
     */
    @Override
    public Observable<List<Language>> getSupportLanguages(String ui) {
        List<Language> cachedLangs = realmHelper.readAllSupportLanguages();

        Observable<List<Language>> observable = translateApi
                .getSupportLanguages(Data.TRANSLATE_API_KEY, ui)
                .subscribeOn(Schedulers.io())
                .map(mMapper::transform)
                .observeOn(Schedulers.computation())
                .doOnNext(languages -> {
                    for (Language language: languages)
                        realmHelper.writeLanguageResponseToRealm(language);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(languages -> realmHelper.readAllSupportLanguages())
                .onErrorReturn(throwable -> {
                    Log.d("tag_", "code: " + ((HttpException)throwable).code());
                    return cachedLangs;
                });

        if (cachedLangs != null){
            Log.d("tag_", "cached rep: " + cachedLangs);
            observable = Observable.mergeDelayError(observable, Observable.just(cachedLangs));
        }

        return observable;
    }

    private Observable<Translation> translateWithoutDict(String text, String lang) {
        return translateApi
                .translate(Data.TRANSLATE_API_KEY, text, lang)
                .map(mMapper::transform);
    }

    @Override
    public Observable<Translation> translate(String text, String lang, String ui) {
        return Observable.zip(translateWithoutDict(text, lang),
                getDictionary(text, lang, ui),((translation, dictionaryResponse) -> {
                    if (translation != null && dictionaryResponse != null) {
                        translation.setDefList(dictionaryResponse.getDef());
                    }
                    return translation;
                }))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Translation> translate(String text) {
        return null;
    }

    @Override
    public List<Translation> getHistory() {
        return realmHelper.getHistory();
    }

    public List<Translation> getFavorites() {
        return realmHelper.getFavorites();
    }

    @Override
    public Observable<Boolean> saveTranslation(Translation translation) {
        return realmHelper.writeTranslationToRealm(translation);
    }

    @Override
    public Observable<Boolean> deleteHistory() {
        return realmHelper.clearHistory();
    }

    @Override
    public Observable<Boolean> deleteFavorites() {
        return realmHelper.clearFavorites();
    }

    @Override
    public Observable<DictionaryResponse> getDictionary(String text, String lang, String ui) {
        return dictionaryApi
                .getDictionary(Data.DICTIONARY_API_KEY, lang, text, ui);
    }
}
