package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwerty.yandextranslate.common.utils.Utils;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Def extends RealmObject{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("tr")
    @Expose
    private RealmList<Tr> tr = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public RealmList<Tr> getTr() {
        return tr;
    }

    public void setTr(RealmList<Tr> tr) {
        this.tr = tr;
    }

    @Override
    public String toString() {
        return "Def{" +
                "text='" + text + '\'' +
                ", pos='" + pos + '\'' +
                ", ts='" + ts + '\'' +
                ", tr=" + Utils.realmListToString(tr) +
                '}';
    }
}