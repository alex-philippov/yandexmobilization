package com.qwerty.yandextranslate.model.data.network.responses.dictionary_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Mean extends RealmObject {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mean{" +
                "text='" + text + '\'' +
                '}';
    }
}
