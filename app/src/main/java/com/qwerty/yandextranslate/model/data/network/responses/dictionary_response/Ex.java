package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwerty.yandextranslate.common.utils.Utils;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Ex extends RealmObject {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("tr")
    @Expose
    private RealmList<Tr_> tr = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RealmList<Tr_> getTr() {
        return tr;
    }

    public void setTr(RealmList<Tr_> tr) {
        this.tr = tr;
    }

    @Override
    public String toString() {
        return "Ex{" +
                "text='" + text + '\'' +
                ", tr=" + Utils.realmListToString(tr) +
                '}';
    }
}
