package com.zhiyong.myapp.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;

public class BaseDialogFragment extends BaseFragment implements BaseMvpView {
    public MaterialDialog progressDialog, alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onReload() {
    }
}
