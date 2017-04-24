package com.qwerty.yandextranslate.model.data.network.responses;

import com.google.gson.annotations.SerializedName;

public class DetectLanguageResponse {
    @SerializedName("code")
    private String mLangCode;

    @SerializedName("lang")
    private String mLangName;

    public DetectLanguageResponse(String langName, String langCode) {
        this.mLangName = langName;
        this.mLangCode = langCode;
    }

    public String getLangCode() {
        return mLangCode;
    }

    public void setLangCode(String langCode) {
        this.mLangCode = langCode;
    }

    public String getLangName() {
        return mLangName;
    }

    public void setLangName(String langName) {
        this.mLangName = langName;
    }
}
