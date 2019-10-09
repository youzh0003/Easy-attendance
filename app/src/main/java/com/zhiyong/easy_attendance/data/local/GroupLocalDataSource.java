package com.zhiyong.easy_attendance.data.local;

import com.zhiyong.easy_attendance.data.GroupDataSource;
import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.realm.Realm;

@Singleton
public class GroupLocalDataSource implements GroupDataSource {
    @Override
    public Flowable<List<Group>> getGroupList() {
        try(Realm realm = Realm.getDefaultInstance()){
            List<Group> groupList = realm.where(Group.class)
                    .findAll();
            if(groupList != null){
                return Flowable.just(realm.copyFromRealm(groupList));
            }
            return Flowable.empty();
        }
    }

    @Override
    public boolean isGroupExist(String groupName) {
        try(Realm realm = Realm.getDefaultInstance()){
            Group group = realm.where(Group.class)
                    .equalTo(Group.GROUP_NAME, groupName)
                    .findFirst();
            return group != null;
        }
    }

    @Override
    public Completable createGroup(String groupName, String remark) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                Group group = new Group(groupName, remark);
                realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(group));
            }
        });
    }

    @Override
    public Completable updateGroup(Group group) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(group));
            }
        });
    }

    @Override
    public Completable deleteGroup(Group group) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                realm.executeTransaction(realm1 -> {
                    realm1.where(Group.class)
                            .equalTo(Group.GROUP_NAME, group.getName())
                            .findAll()
                            .deleteAllFromRealm();
                });
            }
        });
    }
}
