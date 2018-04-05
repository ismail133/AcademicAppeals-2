package io.academicappeals.a1413249.academicappeals;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Start Realm
        Realm.init(this);
        // Initialise the base Realm db
        initRealmConfig();
    }

    private void initRealmConfig() {
        RealmConfiguration baseConfig = new RealmConfiguration.Builder()
                .name("db.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(baseConfig);
    }
}