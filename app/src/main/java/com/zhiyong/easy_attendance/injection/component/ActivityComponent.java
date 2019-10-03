package com.zhiyong.easy_attendance.injection.component;

import com.zhiyong.easy_attendance.ui.home.MainActivity;
import com.zhiyong.easy_attendance.injection.PerActivity;
import com.zhiyong.easy_attendance.injection.module.ActivityModule;
import com.zhiyong.easy_attendance.ui.qr.QRCodeRecognitionFragment;

import dagger.Component;

/**
 * Created by Zhiyong on 3/28/2019.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)

public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(QRCodeRecognitionFragment qrCodeRecognitionFragment);
}
