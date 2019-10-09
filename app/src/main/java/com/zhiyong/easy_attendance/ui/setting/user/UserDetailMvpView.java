package com.zhiyong.easy_attendance.ui.setting.user;

import com.zhiyong.easy_attendance.base.BaseMvpView;
import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

public interface UserDetailMvpView extends BaseMvpView {
    void getGroupsSuccess(List<Group> groups);

    void getGroupsFailed();

    void createUserSuccess();

    void createUserFailed();

    void updateUserSuccess();

    void updateUserFailed();
}
