package com.qwerty.yandextranslate.view.interfaces;

import com.arellomobile.mvp.MvpView;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

import java.util.List;

import io.reactivex.Observable;

public interface TranslateView extends MvpView {

    void onSupportLangsLoadError(String message);
    //@StateStrategyType(SkipStrategy.class)
    void setSupportLangs(List<Language> langs);

    void restoreSpinnerLang();

    void showLoading();
    void hideLoading();

    void showTranslation(Translation translation);
    void showSavedTranslation(Translation translation);

}
