package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.injection.Local;
import com.zhiyong.easy_attendance.injection.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class UserRepository implements UserDataSource {
    private final UserDataSource userLocalDataSource;
    private final UserDataSource userRemoteDataSource;

    @Inject
    public UserRepository(@Local UserDataSource userLocalDataSource, @Remote UserDataSource userRemoteDataSource) {
        this.userLocalDataSource = userLocalDataSource;
        this.userRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public Flowable<List<Person>> getUserList() {
        return userLocalDataSource.getUserList();
    }

    @Override
    public boolean isUserExist(String id) {
        return userLocalDataSource.isUserExist(id);
    }

    @Override
    public Completable deleteUser(Person person) {
        return userLocalDataSource.deleteUser(person);
    }

    @Override
    public Completable createUser(String name, String id, Group group) {
        return userLocalDataSource.createUser(name, id, group);
    }

    @Override
    public Completable updateUser(Person person) {
        return userLocalDataSource.updateUser(person);
    }
}
