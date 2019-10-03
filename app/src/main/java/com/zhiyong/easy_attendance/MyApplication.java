package com.zhiyong.easy_attendance;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.zhiyong.easy_attendance.injection.component.ApplicationComponent;
import com.zhiyong.easy_attendance.injection.component.DaggerApplicationComponent;
import com.zhiyong.easy_attendance.injection.module.ApplicationModule;

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
