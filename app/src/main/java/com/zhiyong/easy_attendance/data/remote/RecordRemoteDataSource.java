package com.zhiyong.easy_attendance.data.remote;

import com.zhiyong.easy_attendance.data.RecordDataSource;
import com.zhiyong.easy_attendance.data.models.Record;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class RecordRemoteDataSource implements RecordDataSource {
    @Inject
    public RecordRemoteDataSource() {
    }

    @Override
    public Completable createRecord(Record record) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<List<Record>> getRecordList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<List<Record>> getRecordListQuery(String date, String groupName) {
        throw new UnsupportedOperationException();
    }
}
