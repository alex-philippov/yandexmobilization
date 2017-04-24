package com.qwerty.yandextranslate.model.data.network.responses;


import android.util.ArrayMap;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SupportLanguageResponse {
    @SerializedName("langs")
    private ArrayMap<String, String> mLangs;


    public ArrayMap<String, String> getLangs() {
        return mLangs;
    }

    public void setLangs(ArrayMap<String, String> mLangs) {
        this.mLangs = mLangs;
    }
}
