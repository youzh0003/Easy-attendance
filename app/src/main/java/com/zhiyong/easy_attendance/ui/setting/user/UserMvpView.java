package com.zhiyong.easy_attendance.ui.setting.user;

import com.zhiyong.easy_attendance.base.BaseMvpView;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.List;

public interface UserMvpView extends BaseMvpView {
    void getUsersSuccess(List<Person> users);

    void getUsersFailed();
}
