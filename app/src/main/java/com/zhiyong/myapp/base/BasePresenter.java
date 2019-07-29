package com.zhiyong.myapp.base;

/**
 * Created by Zhiyong on 3/28/2019.
 */

public class BasePresenter<V extends MvpView> implements Presenter<V> {
    private V mMvpView;

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

}
