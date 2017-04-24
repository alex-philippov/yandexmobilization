package com.qwerty.yandextranslate.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferenceManager {
    private Context mContext;

    @Inject
    public SharedPreferenceManager(Context context) {
        mContext = context;
    }

    public void setIsLangsCached(boolean isCached) {
        SharedPreferences prefs = mContext
                .getSharedPreferences(Data.YANDEX_TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit()
                .putBoolean(Data.LANGS_CACHED_PREFERENCE_KEY, isCached)
                .apply();
    }

    public boolean isLangsCached() {
        SharedPreferences prefs = mContext
                .getSharedPreferences(Data.YANDEX_TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getBoolean(Data.LANGS_CACHED_PREFERENCE_KEY, false);
    }
}
