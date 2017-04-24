package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Syn extends RealmObject{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("gen")
    @Expose
    private String gen;

    public  Syn() {
    }

    public Syn(String text, String pos, String gen) {
        this.text = text;
        this.pos = pos;
        this.gen = gen;
    }

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

    @Override
    public String toString() {
        return "Syn{" +
                "text='" + text + '\'' +
                ", pos='" + pos + '\'' +
                ", gen='" + gen + '\'' +
                '}';
    }
}
