package com.qwerty.yandextranslate.model.data.network;

import java.io.IOError;
import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "Ошибка подключения к интернету";
    }
}
