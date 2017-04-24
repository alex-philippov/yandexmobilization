package com.qwerty.yandextranslate.model.data.network.responses;

import com.google.gson.annotations.SerializedName;
import com.qwerty.yandextranslate.model.domain.Entities.Translation;

public class TranslationResponse {
    @SerializedName("code")
    private int mCode;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("text")
    private String[] mText;

    public TranslationResponse(int code, String lang, String[] text) {
        this.mCode = code;
        this.mLang = lang;
        this.mText = text;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public String getLang() {
        return mLang;
    }

    public void setLang(String lang) {
        this.mLang = lang;
    }

    public String[] getText() {
        return mText;
    }

    public void setText(String[] text) {
        this.mText = text;
    }
}
