package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class RecordReporsitory implements RecordDataSource {
    private final RecordDataSource recordLocalDataSource;
    private final RecordDataSource recordRemoteDataSource;

    @Inject
    public RecordReporsitory(@Local RecordDataSource recordLocalDataSource, @Remote RecordDataSource recordRemoteDataSource) {
        this.recordLocalDataSource = recordLocalDataSource;
        this.recordRemoteDataSource = recordRemoteDataSource;
    }

    @Override
    public Completable createRecord(Record record) {
        return recordLocalDataSource.createRecord(record);
    }

    @Override
    public Flowable<List<Record>> getRecordList() {
        return recordLocalDataSource.getRecordList();
    }

    @Override
    public Flowable<List<Record>> getRecordListQuery(String date, String groupName) {
        return recordLocalDataSource.getRecordListQuery(date, groupName);
    }
}
