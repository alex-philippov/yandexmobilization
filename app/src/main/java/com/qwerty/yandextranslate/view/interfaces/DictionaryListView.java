package com.qwerty.yandextranslate.view.interfaces;

import com.arellomobile.mvp.MvpView;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

import java.util.List;

public interface DictionaryListView extends MvpView{
    void showList(List<Translation> list);
    void showTranslation();
}
