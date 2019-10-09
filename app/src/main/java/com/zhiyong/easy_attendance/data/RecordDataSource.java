package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Record;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface RecordDataSource {
    Completable createRecord(Record record);

    Flowable<List<Record>> getRecordList();

    Flowable<List<Record>> getRecordListQuery(String date, String groupName);
}
