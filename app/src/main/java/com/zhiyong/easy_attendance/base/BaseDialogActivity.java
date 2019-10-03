package com.zhiyong.easy_attendance.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.ui.home.MainActivity;
import com.zhiyong.easy_attendance.utils.AppUtils;

/**
 * Created by Zhiyong on 3/28/2019.
 */

public abstract class BaseDialogActivity extends BaseActivity implements BaseMvpView {
    public MaterialDialog progressDialog, alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlertDialog();
        createProgressDialog();
        setupDialogTitle();
    }

    @Override
    public void createProgressDialog() {
        progressDialog = AppUtils.createProgress(this, getString(R.string.app_title));
    }

    @Override
    public void createAlertDialog() {
        alertDialog = AppUtils.createAlertDialog(this, getString(R.string.app_title));
    }

    protected abstract void setupDialogTitle();

    @Override
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showProgressDialog(boolean value) {
        if (value) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        dismissDialog();
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyError(String error) {
        if (this instanceof MainActivity) {
            ((MainActivity) this).showErrorMessage(error);
        }
    }

    @Override
    public void notifyError(int errorResId) {
        notifyError(getString(errorResId));
    }

    @Override
    public void setProgressDialogCancelable(boolean value) {
        progressDialog.setCancelable(value);
    }
}

