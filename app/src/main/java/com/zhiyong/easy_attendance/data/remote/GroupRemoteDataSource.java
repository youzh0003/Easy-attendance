package com.zhiyong.easy_attendance.data.remote;

import com.zhiyong.easy_attendance.data.GroupDataSource;
import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class GroupRemoteDataSource implements GroupDataSource {
    @Override
    public Flowable<List<Group>> getGroupList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGroupExist(String groupName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable createGroup(String groupName, String remark) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable updateGroup(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable deleteGroup(Group group) {
        throw new UnsupportedOperationException();
    }
}
