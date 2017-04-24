package com.qwerty.yandextranslate.common.utils;

import com.qwerty.yandextranslate.model.data.network.responses.SupportLanguageResponse;
import com.qwerty.yandextranslate.model.data.network.responses.TranslationResponse;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.view.interfaces.TranslateView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;


public class Mapper {
    /**
     * Transform SupportLanguageResponse into a List<Language> and sort
     * @param languageResponse to transform
     * @return List of Language if valid SupportLanguageResponse otherwise null
     */
    public List<Language> transform(SupportLanguageResponse languageResponse) {
        List<Language> list = null;
        if (languageResponse != null) {
            list = new RealmList<>();
            Map<String, String> map = languageResponse.getLangs();
            for (Map.Entry<String, String> entry: map.entrySet()) {
                list.add(new Language(entry.getKey(), entry.getValue()));
            }
            /*Collections.sort(list,
                    (o1, o2) -> o1.getFullName().compareToIgnoreCase(o2.getFullName()));*/
        }
        return list;
    }

    /**
     * Transform list of languages to list with the names of languages
     * @param list list of languages
     * @return list of names
     */
    public List<String> transform(List<Language> list) {
        List<String> result = null;
        if (list != null) {
            result = new ArrayList<>();
            for (Language language: list) {
                result.add(language.getFullName());
            }
        }
        return result;
    }
    /**
     * Transform array of languages names to Languages
     * @param langsName array of String
     * @return list of Languages
     */
    public List<Language> transform(String[] langsName) {
        List<Language> list = null;
        if (langsName != null) {
            list = new ArrayList<>();
            for (String name: langsName) {
                list.add(new Language(null, name));
            }
        }
        return list;
    }

    public Translation transform(TranslationResponse response) {
        return new Translation(response);
    }
}
