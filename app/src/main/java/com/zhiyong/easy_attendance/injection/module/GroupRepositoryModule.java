package com.zhiyong.easy_attendance.injection.module;

import com.zhiyong.easy_attendance.data.GroupDataSource;
import com.zhiyong.easy_attendance.data.local.GroupLocalDataSource;
import com.zhiyong.easy_attendance.data.remote.GroupRemoteDataSource;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupRepositoryModule {
    @Provides
    @Singleton
    @Local
    GroupDataSource provideGroupLocalDataSource(){
        return new GroupLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    GroupDataSource provideGroupRemoteDataSource(){
        return new GroupRemoteDataSource();
    }
}
