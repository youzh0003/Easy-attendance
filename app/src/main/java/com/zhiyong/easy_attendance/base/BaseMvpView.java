package com.zhiyong.easy_attendance.base;

/**
 * Created by Zhiyong on 3/28/2019.
 */

public interface BaseMvpView extends MvpView{
    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog(boolean value);

    void setProgressDialogCancelable(boolean value);

    void showAlertDialog(String errorMessage);

    void dismissDialog();

    void notifyError(String error);

    void notifyError(int errorResId);
}
