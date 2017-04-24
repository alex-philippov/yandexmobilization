package com.qwerty.yandextranslate.model.domain.Entities;

import android.util.Log;

import com.qwerty.yandextranslate.common.utils.Utils;
import com.qwerty.yandextranslate.model.data.network.responses.TranslationResponse;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Def;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Syn;
import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.Tr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class Translation extends RealmObject {
    private int mCode;
    private String mLang;
    private String mOriginalText;
    private String mTranslatedText;
    private Language mFromLang;
    private Language mToLang;
    private RealmList<Def> mDefList;
    private boolean mInFavorites;
    private boolean mInHistory;
    private long mDate;

    public Translation() {
        mDate = new Date().getTime();
    }

    public Translation(int code, String lang, String translation, String originalText) {
        this.mCode = code;
        this.mLang = lang;
        this.mTranslatedText = translation;
        this.mOriginalText = originalText;
        mDate = new Date().getTime();
    }

    public Translation(TranslationResponse translationResponse) {
        if (translationResponse != null) {
            mCode = translationResponse.getCode();
            mLang = translationResponse.getLang();
            mTranslatedText = Utils.arrayToString(translationResponse.getText());
        }
        mDate = new Date().getTime();
    }

    public Translation(String text, Language from, Language to) {
        mOriginalText = text;
        mFromLang = from;
        mToLang = to;
        mDate = new Date().getTime();
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

    public String getTranslation() {
        return mTranslatedText;
    }

    public void setTranlation(String translatedText) {
        this.mTranslatedText = translatedText;
    }

    public String getOriginalText() {
        return mOriginalText;
    }

    public void setOriginalText(String originalText) {
        this.mOriginalText = originalText;
    }

    public Language getFromLang() {
        return mFromLang;
    }

    public void setFromLang(Language fromLang) {
        this.mFromLang = fromLang;
    }

    public Language getToLang() {
        return mToLang;
    }

    public void setToLang(Language toLang) {
        this.mToLang = toLang;
    }

    public RealmList<Def> getDefList() {
        return mDefList;
    }

    public void setDefList(RealmList<Def> defList) {
        mDefList = defList;
        correctSyns();
    }

    public boolean isFavorites() {
        return mInFavorites;
    }

    public void setFavorites(boolean savedToFavorites) {
        mInFavorites = savedToFavorites;
    }

    public boolean isHistory() {
        return mInHistory;
    }

    public void setHistory(boolean inHistory) {
        mInHistory = inHistory;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "mCode=" + mCode +
                ", mLang='" + mLang + '\'' +
                ", mOriginalText='" + mOriginalText + '\'' +
                ", mTranslatedText=" + mTranslatedText +
                ", mFromLang=" + mFromLang +
                ", mToLang=" + mToLang +
                ", mDefList=" + Utils.realmListToString(mDefList) +
                '}';
    }

    public void createFrom(Translation translation, Realm realm) {
        mInFavorites = translation.isFavorites();
        mTranslatedText = translation.getTranslation();
        mToLang = translation.getToLang();
        mFromLang = translation.getFromLang();
        mCode = translation.getCode();
        mLang = translation.getLang();
        mOriginalText = translation.getOriginalText();
        if (!translation.getDefList().isManaged()) {
            RealmList<Def> managetList = new RealmList<>();
            for (Def def: translation.getDefList()) {
                if (def.isManaged()) {
                    managetList.add(def);
                } else {
                    managetList.add(realm.copyToRealm(def));
                }
            }
            mDefList = managetList;
        } else {
            mDefList = translation.getDefList();
        }
    }

    public void update() {
        mDate = new Date().getTime();
    }

    private void correctSyns() {
        if (mDefList != null) {
            for (Def def: mDefList) {
                List<Tr> trList = def.getTr();
                if (trList != null) {
                    for (int i = 0; i < trList.size(); i++) {
                        Log.d("tag__", "syyyynn");
                        if (trList.get(i).getSyn() == null)
                            trList.get(i).setSyn(new RealmList<>());
                        trList.get(i).getSyn().add(0, new Syn(
                                trList.get(i).getText(),
                                trList.get(i).getPos(),
                                trList.get(i).getGen()));
                    }
                }
            }
        }
    }
}
