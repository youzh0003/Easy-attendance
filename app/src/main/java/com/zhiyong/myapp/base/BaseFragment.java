package com.zhiyong.myapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zhiyong.myapp.MyApplication;
import com.zhiyong.myapp.R;
import com.zhiyong.myapp.injection.component.ActivityComponent;
import com.zhiyong.myapp.injection.component.DaggerActivityComponent;
import com.zhiyong.myapp.injection.module.ActivityModule;

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
        replaceFragment(fragment, containerId, 0, true);
    }

    public void replaceFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        replaceFragment(fragment, containerId, 0, addToBackStack);
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
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (customAnimations == IN_LEFT_OUT_RIGHT) {
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
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

