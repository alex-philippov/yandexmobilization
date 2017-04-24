package com.qwerty.yandextranslate.common.dagger.modules;

import android.content.Context;

import com.qwerty.yandextranslate.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
