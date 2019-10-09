package com.zhiyong.easy_attendance.data.local;

import com.zhiyong.easy_attendance.data.RecordDataSource;
import com.zhiyong.easy_attendance.data.models.Record;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.realm.Realm;

@Singleton
public class RecordLocalDataSource implements RecordDataSource {
    @Inject
    public RecordLocalDataSource() {
    }

    @Override
    public Completable createRecord(Record record) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(record));
            }
        });
    }

    @Override
    public Flowable<List<Record>> getRecordList() {
        try(Realm realm = Realm.getDefaultInstance()){
            List<Record> list = realm.where(Record.class)
                    .findAll();
            if(list != null){
                return Flowable.just(realm.copyFromRealm(list));
            }
            return Flowable.empty();
        }
    }

    @Override
    public Flowable<List<Record>> getRecordListQuery(String date, String groupName) {
        try(Realm realm = Realm.getDefaultInstance()){
            List<Record> list = realm.where(Record.class)
                    .contains(Record.DATE, date)
                    .equalTo(Record.PERSON_GROUP_NAME, groupName)
                    .findAll();
            if(list != null){
                return Flowable.just(realm.copyFromRealm(list));
            }
            return Flowable.empty();
        }
    }
}
