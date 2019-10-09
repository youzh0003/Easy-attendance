package com.zhiyong.easy_attendance.ui.setting.user;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.domain.CreateUserUseCase;
import com.zhiyong.easy_attendance.domain.GetGroupsUseCase;
import com.zhiyong.easy_attendance.domain.GetUsersUseCase;
import com.zhiyong.easy_attendance.domain.UpdateUserUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class UserDetailPresenter extends BasePresenter<UserDetailMvpView> {
    private final GetGroupsUseCase getGroupsUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    @Inject
    public UserDetailPresenter(GetGroupsUseCase getGroupsUseCase,
                               GetUsersUseCase getUsersUseCase,
                               CreateUserUseCase createUserUseCase,
                               UpdateUserUseCase updateUserUseCase) {
        this.getGroupsUseCase = getGroupsUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    public void getGroups() {
        getGroupsUseCase.execute(new DisposableSubscriber<List<Group>>() {
            @Override
            public void onNext(List<Group> groups) {
                if(getMvpView() != null){
                    getMvpView().getGroupsSuccess(groups);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null){
                    getMvpView().getGroupsFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        }, new GetGroupsUseCase.Params());
    }

    boolean isUserIdExist(String query) {
        return getUsersUseCase.isUserIdExist(query);
    }

    public void createNewUser(String name, String id, Group group) {
        createUserUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().createUserSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().createUserFailed();
                }
            }
        }, new CreateUserUseCase.Params(name, id, group));
    }

    public void updateUser(String name, String personalId, Group group) {
        Person person = new Person(personalId, name, group);
        updateUserUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().updateUserSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().updateUserFailed();
                }
            }
        }, new UpdateUserUseCase.Params(person));
    }
    @Override
    public void detachView() {
        super.detachView();
        getGroupsUseCase.dispose();
        getUsersUseCase.dispose();
        createUserUseCase.dispose();
        updateUserUseCase.dispose();
    }
}
