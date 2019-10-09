package com.zhiyong.easy_attendance.ui.setting.group;

import com.zhiyong.easy_attendance.base.BaseMvpView;
import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

public interface GroupMvpView extends BaseMvpView {
    void getGroupsSuccess(List<Group> groups);

    void getGroupsFailed();

    void deleteGroupsSuccess(String name);

    void deleteGroupsFailed();
}
