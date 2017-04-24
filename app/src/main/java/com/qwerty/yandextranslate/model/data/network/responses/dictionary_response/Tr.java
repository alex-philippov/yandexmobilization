package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwerty.yandextranslate.common.utils.Utils;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Tr extends RealmObject{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("gen")
    @Expose
    private String gen;
    @SerializedName("syn")
    @Expose
    private RealmList<Syn> syn = null;
    @SerializedName("mean")
    @Expose
    private RealmList<Mean> mean = null;
    @SerializedName("ex")
    @Expose
    private RealmList<Ex> ex = null;
    @SerializedName("asp")
    @Expose
    private String asp;

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

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public RealmList<Syn> getSyn() {
        return syn;
    }

    public void setSyn(RealmList<Syn> syn) {
        this.syn = syn;
    }

    public RealmList<Mean> getMean() {
        return mean;
    }

    public void setMean(RealmList<Mean> mean) {
        this.mean = mean;
    }

    public RealmList<Ex> getEx() {
        return ex;
    }

    public void setEx(RealmList<Ex> ex) {
        this.ex = ex;
    }

    public String getAsp() {
        return asp;
    }

    public void setAsp(String asp) {
        this.asp = asp;
    }

    @Override
    public String toString() {
        return "Tr{" +
                "text='" + text + '\'' +
                ", pos='" + pos + '\'' +
                ", gen='" + gen + '\'' +
                ", syn=" + Utils.realmListToString(syn) +
                ", mean=" + Utils.realmListToString(mean) +
                ", ex=" + Utils.realmListToString(ex) +
                ", asp='" + asp + '\'' +
                '}';
    }
}
