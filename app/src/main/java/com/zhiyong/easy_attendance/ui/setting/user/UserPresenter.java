package com.zhiyong.easy_attendance.ui.setting.user;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.domain.GetUsersUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class UserPresenter extends BasePresenter<UserMvpView> {
    private final GetUsersUseCase getUsersUseCase;
    @Inject
    public UserPresenter(GetUsersUseCase getUsersUseCase) {
        this.getUsersUseCase = getUsersUseCase;
    }

    public void getUsers() {
        getUsersUseCase.execute(new DisposableSubscriber<List<Person>>() {
            @Override
            public void onNext(List<Person> users) {
                if(getMvpView() != null){
                    getMvpView().getUsersSuccess(users);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null){
                    getMvpView().getUsersFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        }, new GetUsersUseCase.Params());
    }

    public void deleteUser(Person user) {
    }

    @Override
    public void detachView() {
        super.detachView();
        getUsersUseCase.dispose();
    }
}
