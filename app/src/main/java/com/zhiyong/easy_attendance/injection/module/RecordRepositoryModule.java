package com.zhiyong.easy_attendance.injection.module;

import com.zhiyong.easy_attendance.data.RecordDataSource;
import com.zhiyong.easy_attendance.data.local.RecordLocalDataSource;
import com.zhiyong.easy_attendance.data.remote.RecordRemoteDataSource;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecordRepositoryModule {
    @Provides
    @Singleton
    @Local
    RecordDataSource provideLocalRecordDataSource(){
        return new RecordLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    RecordDataSource provideRemoteRecordDataSource(){
        return new RecordRemoteDataSource();
    }
}
