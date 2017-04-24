package com.qwerty.yandextranslate.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qwerty.yandextranslate.App;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.model.domain.interactors.TranslationGetterInteractor;
import com.qwerty.yandextranslate.view.interfaces.DictionaryListView;

import javax.inject.Inject;

@InjectViewState
public class FavoritesListPresenter extends MvpPresenter<DictionaryListView>
        implements SelectiveItems {

    public static final String TAG = "tag_favorites_presenter";

    @Inject
    TranslationGetterInteractor translationGetter;

    public FavoritesListPresenter() {
        App.getDataComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showList(translationGetter.getFavorites());

        translationGetter
                .getDeleteObservable()
                .subscribe(deleted -> {
                    if (deleted) {
                        getViewState().showList(translationGetter.getFavorites());
                    }
                });

        translationGetter
                .getSavedObservable()
                .subscribe(saved -> {
                    if (saved) {
                        getViewState().showList(translationGetter.getFavorites());
                    }
                });

        translationGetter
                .getCheckedObservable()
                .subscribe(checkedChange -> {
                    getViewState().showList(translationGetter.getFavorites());
                });
    }

    @Override
    public void attachView(DictionaryListView view) {
        super.attachView(view);
        Log.d(TAG, "pres dic attach " + view.toString());

    }

    public void onFocusCleared(Translation translation) {

    }


    @Override
    public void onItemSelected(Translation translation) {
        translationGetter
                .getTranslationSetter()
                .onNext(translation);
        getViewState().showTranslation();
    }

    @Override
    public void onItemChangeChecked() {
        translationGetter.checkChanged();
    }
}
