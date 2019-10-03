package com.zhiyong.myapp.injection.component;

import com.zhiyong.myapp.ui.home.MainActivity;
import com.zhiyong.myapp.injection.PerActivity;
import com.zhiyong.myapp.injection.module.ActivityModule;
import com.zhiyong.myapp.ui.qr.QRCodeRecognitionFragment;

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
