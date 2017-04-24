package com.qwerty.yandextranslate.model.domain;

import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.DictionaryResponse;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<List<Language>> getSupportLanguages(String ui);
    Observable<Translation> translate(String text, String lang, String ui);
    Observable<Translation> translate(String text);
    List<Translation> getHistory();
    Observable<Boolean> saveTranslation(Translation translation);
    Observable<Boolean> deleteHistory();
    Observable<Boolean> deleteFavorites();

    List<Translation> getFavorites();

    Observable<DictionaryResponse> getDictionary(String text, String lang, String ui);
}
