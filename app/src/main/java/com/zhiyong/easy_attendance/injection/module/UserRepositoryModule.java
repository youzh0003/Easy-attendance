package com.zhiyong.easy_attendance.injection.module;

import com.zhiyong.easy_attendance.data.UserDataSource;
import com.zhiyong.easy_attendance.data.local.UserLocalDataSource;
import com.zhiyong.easy_attendance.data.remote.UserRemoteDataSource;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class UserRepositoryModule {
    @Provides
    @Inject
    @Local
    UserDataSource provideLocalDataSource(){
        return new UserLocalDataSource();
    }

    @Provides
    @Inject
    @Remote
    UserDataSource provideRemoteDataSource(){
        return new UserRemoteDataSource();
    }
}
