package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class DictionaryResponse {

    @SerializedName("def")
    @Expose
    private RealmList<Def> def = null;

    public RealmList<Def> getDef() {
        return def;
    }

    public void setDef(RealmList<Def> def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return "DictionaryResponse{" +
                "def=" + (List)def +
                '}';
    }
}
