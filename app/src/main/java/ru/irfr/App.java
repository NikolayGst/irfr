package ru.irfr;

import android.app.Application;
import android.content.Context;
import com.karumi.dexter.Dexter;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("irfr.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }

}
