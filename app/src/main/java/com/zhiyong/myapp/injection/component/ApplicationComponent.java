package com.zhiyong.myapp.injection.component;

import android.app.Application;
import android.content.Context;

import com.zhiyong.myapp.injection.ApplicationContext;
import com.zhiyong.myapp.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {
    @ApplicationContext
    Context context();

    Application application();
}
