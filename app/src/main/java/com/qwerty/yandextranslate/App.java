package com.qwerty.yandextranslate;

import android.app.Application;
import android.util.Log;

import com.qwerty.yandextranslate.common.dagger.components.DaggerDataComponent;
import com.qwerty.yandextranslate.common.dagger.components.DaggerDomainComponent;
import com.qwerty.yandextranslate.common.dagger.components.DataComponent;
import com.qwerty.yandextranslate.common.dagger.components.DomainComponent;
import com.qwerty.yandextranslate.common.dagger.modules.AppModule;
import com.qwerty.yandextranslate.common.dagger.modules.InteractorsModule;
import com.qwerty.yandextranslate.common.dagger.modules.RepositoriesModule;

import io.realm.Realm;

public class App extends Application {
    private static DataComponent sDataComponent;
    private static DomainComponent sDomainComponent;

    public static DataComponent getDataComponent() {
        return sDataComponent;
    }

    public static DomainComponent getDomainComponent() {
        return sDomainComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        sDataComponent = buildDataComponent();
        sDomainComponent = buildDomainComponent();
    }

    protected DataComponent buildDataComponent() {
        Log.d("tag_app", "buildData");
        return DaggerDataComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    protected DomainComponent buildDomainComponent() {
        return DaggerDomainComponent.builder()
                .build();
    }
}
