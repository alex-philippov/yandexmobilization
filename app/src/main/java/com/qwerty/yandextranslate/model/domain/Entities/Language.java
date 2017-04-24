package com.qwerty.yandextranslate.model.domain.Entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Language extends RealmObject {
    @PrimaryKey
    private String name;
    private String fullName;

    public Language() {
    }

    public Language(String langName, String langFullName) {
        this.name = langName;
        this.fullName = langFullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
