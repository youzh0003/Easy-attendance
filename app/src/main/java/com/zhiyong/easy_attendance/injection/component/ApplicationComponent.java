package com.zhiyong.easy_attendance.injection.component;

import android.app.Application;
import android.content.Context;

import com.zhiyong.easy_attendance.data.GroupRepository;
import com.zhiyong.easy_attendance.data.RecordReporsitory;
import com.zhiyong.easy_attendance.data.UserRepository;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;
import com.zhiyong.easy_attendance.injection.ApplicationContext;
import com.zhiyong.easy_attendance.injection.module.ApplicationModule;
import com.zhiyong.easy_attendance.injection.module.GroupRepositoryModule;
import com.zhiyong.easy_attendance.injection.module.RecordRepositoryModule;
import com.zhiyong.easy_attendance.injection.module.UserRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        GroupRepositoryModule.class,
        UserRepositoryModule.class,
        RecordRepositoryModule.class
})
public interface ApplicationComponent {
    @ApplicationContext
    Context context();

    Application application();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    GroupRepository groupRepository();

    UserRepository userRepository();

    RecordReporsitory recordReporsitory();
}
