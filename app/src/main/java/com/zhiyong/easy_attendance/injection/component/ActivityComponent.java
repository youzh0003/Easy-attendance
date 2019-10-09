package com.zhiyong.easy_attendance.injection.component;

import com.zhiyong.easy_attendance.injection.PerActivity;
import com.zhiyong.easy_attendance.injection.module.ActivityModule;
import com.zhiyong.easy_attendance.ui.home.MainActivity;
import com.zhiyong.easy_attendance.ui.qr.QRCodeRecognitionFragment;
import com.zhiyong.easy_attendance.ui.setting.group.GroupDetailView;
import com.zhiyong.easy_attendance.ui.setting.group.GroupView;
import com.zhiyong.easy_attendance.ui.setting.record.RecordView;
import com.zhiyong.easy_attendance.ui.setting.user.UserDetailView;
import com.zhiyong.easy_attendance.ui.setting.user.UserView;

import dagger.Component;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)

public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(QRCodeRecognitionFragment qrCodeRecognitionFragment);

    void inject(GroupView groupView);

    void inject(UserView userView);

    void inject(RecordView recordView);

    void inject(UserDetailView userDetailView);

    void inject(GroupDetailView groupDetailView);
}
