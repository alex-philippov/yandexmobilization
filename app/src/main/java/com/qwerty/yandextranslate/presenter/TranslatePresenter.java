package com.qwerty.yandextranslate.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qwerty.yandextranslate.App;
import com.qwerty.yandextranslate.R;
import com.qwerty.yandextranslate.common.utils.SharedPreferenceManager;
import com.qwerty.yandextranslate.model.data.network.NoConnectivityException;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;
import com.qwerty.yandextranslate.model.domain.interactors.SupportLangsGetterInteractor;
import com.qwerty.yandextranslate.model.domain.Entities.Language;
import com.qwerty.yandextranslate.model.domain.interactors.TranslationGetterInteractor;
import com.qwerty.yandextranslate.view.interfaces.TranslateView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {
    public static final String TAG = "tag_translate_presenter";

    @Inject
    SupportLangsGetterInteractor langsInteractor;
    @Inject
    TranslationGetterInteractor translationInteractor;
    @Inject
    Context context;
    @Inject
    SharedPreferenceManager prefManager;

    private Disposable uiDisposable;
    private CompositeDisposable modelDisposable;
    private Translation currentTranslation;

    public TranslatePresenter() {
        App.getDataComponent().inject(this);
        modelDisposable = new CompositeDisposable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        checkLangsCached();
        loadLangs();
        translationInteractor
                .getTranslationSetter()
                .subscribe(
                        translation -> getViewState().showSavedTranslation(translation));
    }

    @Override
    public void attachView(TranslateView view) {
        super.attachView(view);
    }


    public void onDestroyFragment() {
        if (!uiDisposable.isDisposed())
            uiDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!modelDisposable.isDisposed())
            modelDisposable.dispose();
    }

    public void loadLangs() {
        modelDisposable.add(
                langsInteractor
                    .getLanguages()
                    .subscribe(this::onLangsLoaded, this::onLangsLoadError));
    }

    private void onLangsLoaded(List<Language> list) {
        if (list != null && list.size() != 0) {
            prefManager.setIsLangsCached(true);
            getViewState().setSupportLangs(list);
            getViewState().restoreSpinnerLang();
        } else {
            prefManager.setIsLangsCached(false);
        }
    }

    private void onLangsLoadError(Throwable e) {
        if (e instanceof NoConnectivityException)
            getViewState().onSupportLangsLoadError(e.getMessage());
        Log.d(TAG, "error: " + e.toString());
    }

    public void translate(Translation translation) {
        modelDisposable.add(
                translationInteractor
                        .translate(translation)
                        .subscribe(this::onGetTranslation, this::onLangsLoadError));
    }

    private void onGetTranslation(Translation translation) {
        Log.d(TAG, "get translation: " + translation.toString());
        currentTranslation = translation;
        getViewState().showTranslation(translation);
    }

    private void checkLangsCached() {
        boolean langsCached = prefManager.isLangsCached();
        if (!langsCached) {
            String[] defLangs = context.getResources().getStringArray(R.array.default_languages);
            ArrayList<Language> list = new ArrayList<>();
            for (String lang: defLangs) {
                String[] tmp = lang.split(" ");
                list.add(new Language(tmp[1], tmp[0]));
            }
            getViewState().setSupportLangs(list);
        }
    }

    public void subscribeUi(Observable<String> inputTextObservable,
                            Observable<Language> fromLangObservable,
                            Observable<Language> toLangObservable) {
        uiDisposable = Observable.combineLatest(
                inputTextObservable,
                fromLangObservable,
                toLangObservable,
                Translation::new)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::translate);
    }


    public void saveTranslation() {
        if (currentTranslation != null)
            translationInteractor.saveTranslation(currentTranslation);
    }
}
