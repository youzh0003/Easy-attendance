package com.zhiyong.easy_attendance.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhiyong.easy_attendance.MyApplication;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.injection.component.ActivityComponent;
import com.zhiyong.easy_attendance.injection.component.DaggerActivityComponent;
import com.zhiyong.easy_attendance.injection.module.ActivityModule;

public class BaseFragment extends Fragment {
    public static final int IN_RIGHT_OUT_LEFT = 1;
    public static final int IN_LEFT_OUT_RIGHT = -1;
    public static final int WITHOUT_ANIMATION = 0;
    protected FragmentActivity activity;
    ActivityComponent mActivityComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(MyApplication.get(getActivity()).getComponent())
                    .activityModule(new ActivityModule(getActivity()))
                    .build();
        }
        return mActivityComponent;
    }


    public void replaceFragment(Fragment fragment, int containerId) {
        replaceFragment(fragment, containerId, IN_RIGHT_OUT_LEFT, true);
    }

    public void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        replaceFragment(fragment, containerId, IN_RIGHT_OUT_LEFT, addToBackStack);
    }

    public void replaceFragment(Fragment fragment, int containerId, int customAnimations, boolean addToBackStack) {
        if (fragment != null) {
            closeSoftKeyboard();
            String backStateName = fragment.getClass().getName();
            FragmentManager fragmentManager = getFragmentManager();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                if (fragmentManager.findFragmentById(containerId) instanceof LoginView) {
//                    fragmentManager.popBackStackImmediate();
//                }
                if (customAnimations == IN_RIGHT_OUT_LEFT) {
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (customAnimations == IN_LEFT_OUT_RIGHT) {
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
                }
                transaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
                if (addToBackStack) {
                    transaction.addToBackStack(fragment.getClass().getSimpleName()).commit();
                } else {
                    transaction.commit();
                }
            }
        }
    }

    public void closeSoftKeyboard() {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}

