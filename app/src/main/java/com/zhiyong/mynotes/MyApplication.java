package com.zhiyong.mynotes;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.zhiyong.mynotes.injection.component.ApplicationComponent;
import com.zhiyong.mynotes.injection.component.DaggerApplicationComponent;
import com.zhiyong.mynotes.injection.module.ApplicationModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Zhiyong on 3/28/2019.
 */

public class MyApplication extends MultiDexApplication {
    private ApplicationComponent mApplicationComponent;

    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public synchronized ApplicationComponent getComponent(){
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
