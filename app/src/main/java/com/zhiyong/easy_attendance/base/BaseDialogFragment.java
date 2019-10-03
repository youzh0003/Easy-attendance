package com.zhiyong.easy_attendance.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.ui.home.MainActivity;
import com.zhiyong.easy_attendance.utils.AppUtils;

public abstract class BaseDialogFragment extends BaseFragment implements BaseMvpView {
    private MaterialDialog progressDialog, alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlertDialog();
        createProgressDialog();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupDialogTitle();
    }

    @Override
    public void createProgressDialog() {
        progressDialog = AppUtils.createProgress(getActivity(), getString(R.string.app_title));
    }

    @Override
    public void createAlertDialog() {
        alertDialog = AppUtils.createAlertDialog(getActivity(), getString(R.string.app_title));
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

    public void clickReload() {
    }

    @Override
    public void onDestroyView() {
        dismissDialog();
        super.onDestroyView();
    }

    @Override
    public void notifyError(String error) {
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).showErrorMessage(error);
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
