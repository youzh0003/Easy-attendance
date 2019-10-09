package com.zhiyong.easy_attendance.ui.setting.group;

import com.zhiyong.easy_attendance.base.BaseMvpView;

public interface GroupDetailMvpView extends BaseMvpView {
    void createGroupSuccess();

    void createGroupFail();

    void updateGroupSuccess();

    void updateGroupFail();
}
