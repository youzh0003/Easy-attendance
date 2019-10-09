package com.zhiyong.easy_attendance.ui.qr;

import com.zhiyong.easy_attendance.base.BaseMvpView;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.List;

public interface QRCodeRecognitionMvpView extends BaseMvpView {
    void getUsersSuccess(List<Person> people);

    void getUsersFailed();

    void createRecordSuccess();

    void createRecordFailed();
}
