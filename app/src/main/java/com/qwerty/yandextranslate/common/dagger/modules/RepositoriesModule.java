package com.qwerty.yandextranslate.common.dagger.modules;


import android.content.Context;

import com.qwerty.yandextranslate.common.utils.Data;
import com.qwerty.yandextranslate.model.data.network.api.DictionaryApi;
import com.qwerty.yandextranslate.model.data.network.api.TranslateApi;
import com.qwerty.yandextranslate.common.utils.Mapper;
import com.qwerty.yandextranslate.model.data.db.RealmHelper;
import com.qwerty.yandextranslate.model.domain.Repository;
import com.qwerty.yandextranslate.model.data.network.RetrofitRepository;
import com.qwerty.yandextranslate.model.data.network.ConnectivityInterceptor;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoriesModule {

    @Provides
    @Singleton
    OkHttpClient provideClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(context))
                .build();
    }

    @Provides
    @Singleton
    TranslateApi provideTranslateApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Data.TRANSLATE_API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(TranslateApi.class);
    }

    @Provides
    @Singleton
    DictionaryApi provideDictionaryApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Data.DICTIONARY_API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(DictionaryApi.class);
    }

    @Provides
    Mapper provideMapper() {
        return new Mapper();
    }

    @Provides
    @Singleton
    Repository provideRepository(TranslateApi api, Mapper mapper) {
        return new RetrofitRepository(api, mapper);
    }

    @Provides
    @Singleton
    RealmHelper provideRealmHelper(Realm realm)  {
        return new RealmHelper(realm);
    }
}
