package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface GroupDataSource {
    Flowable<List<Group>> getGroupList();

    boolean isGroupExist(String groupName);

    Completable createGroup(String groupName, String remark);

    Completable updateGroup(Group group);

    Completable deleteGroup(Group group);
}
