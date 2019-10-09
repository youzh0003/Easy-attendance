package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class GroupRepository implements GroupDataSource {
    private GroupDataSource groupLocalDataSource;
    private GroupDataSource groupRemoteDataSource;

    @Inject
    public GroupRepository(@Local GroupDataSource groupLocalDataSource, @Remote GroupDataSource groupRemoteDataSource) {
        this.groupLocalDataSource = groupLocalDataSource;
        this.groupRemoteDataSource = groupRemoteDataSource;
    }

    @Override
    public Flowable<List<Group>> getGroupList() {
        return groupLocalDataSource.getGroupList();
    }

    @Override
    public boolean isGroupExist(String groupName) {
        return groupLocalDataSource.isGroupExist(groupName);
    }

    @Override
    public Completable createGroup(String groupName, String remark) {
        return groupLocalDataSource.createGroup(groupName, remark);
    }

    @Override
    public Completable updateGroup(Group group) {
        return groupLocalDataSource.updateGroup(group);
    }

    @Override
    public Completable deleteGroup(Group group) {
        return groupLocalDataSource.deleteGroup(group);
    }
}
