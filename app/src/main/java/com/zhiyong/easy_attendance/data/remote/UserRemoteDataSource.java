package com.zhiyong.easy_attendance.data.remote;

import com.zhiyong.easy_attendance.data.UserDataSource;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class UserRemoteDataSource implements UserDataSource {
    @Override
    public Flowable<List<Person>> getUserList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUserExist(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable deleteUser(Person person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable createUser(String name, String id, Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable updateUser(Person person) {
        throw new UnsupportedOperationException();
    }
}
