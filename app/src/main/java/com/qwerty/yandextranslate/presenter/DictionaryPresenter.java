package com.qwerty.yandextranslate.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qwerty.yandextranslate.App;
import com.qwerty.yandextranslate.model.domain.interactors.TranslationGetterInteractor;
import com.qwerty.yandextranslate.view.interfaces.DictionaryView;

import javax.inject.Inject;

@InjectViewState
public class DictionaryPresenter extends MvpPresenter<DictionaryView> {
    public static final String TAG = "tag_dict_pres";

    @Inject
    TranslationGetterInteractor translationGetter;

    public DictionaryPresenter() {
        App.getDataComponent().inject(this);
    }

    public void deleteHistory() {
        Log.d(TAG, "delete history");
        translationGetter.deleteHistory();
    }

    public void deleteFavorites() {
        Log.d(TAG, "delete favorites");
        translationGetter.deleteFavorites();
    }
}
