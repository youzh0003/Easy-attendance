package com.zhiyong.mynotes.base;

/**
 * Created by Zhiyong on 3/28/2019.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
