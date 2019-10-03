package com.zhiyong.easy_attendance.injection.module;

import android.app.Activity;
import android.content.Context;

import com.zhiyong.easy_attendance.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    @Provides
    @ActivityContext
    public Activity provideActivity(){
        return mActivity;
    }

    @Provides
    public Context provideApplication(){
        return mActivity;
    }

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }
}
