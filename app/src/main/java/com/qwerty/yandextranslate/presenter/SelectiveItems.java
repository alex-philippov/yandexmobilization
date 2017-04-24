package com.qwerty.yandextranslate.presenter;

import com.qwerty.yandextranslate.model.domain.Entities.Translation;

public interface SelectiveItems {
    void onItemSelected(Translation translation);
    void onItemChangeChecked();
}
