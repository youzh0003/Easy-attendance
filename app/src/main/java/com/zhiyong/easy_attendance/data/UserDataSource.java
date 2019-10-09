package com.zhiyong.easy_attendance.data;

import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface UserDataSource {
    Flowable<List<Person>> getUserList();

    boolean isUserExist(String name);

    Completable deleteUser(Person person);

    Completable createUser(String name, String id, Group group);

    Completable updateUser(Person person);
}
