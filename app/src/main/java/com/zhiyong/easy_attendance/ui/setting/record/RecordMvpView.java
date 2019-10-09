package com.zhiyong.easy_attendance.ui.setting.record;

import com.zhiyong.easy_attendance.base.BaseMvpView;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Record;

import java.util.List;

public interface RecordMvpView extends BaseMvpView {
    void getRecordSuccess(List<Record> records);

    void getRecordsFailed();

    void getGroupsSuccess(List<Group> groups);

    void getGroupsFailed();
}
