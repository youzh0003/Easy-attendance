package com.zhiyong.mynotes.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zhiyong.mynotes.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideApplication () {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext (){
        return mApplication;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharePreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }
}
