package com.zhiyong.mynotes.injection.component;

import com.zhiyong.mynotes.home.MainActivity;
import com.zhiyong.mynotes.injection.PerActivity;
import com.zhiyong.mynotes.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)

public interface ActivityComponent {
    void inject(MainActivity activity);
}
