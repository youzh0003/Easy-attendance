package com.zhiyong.easy_attendance.data.local;

import com.zhiyong.easy_attendance.data.UserDataSource;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.realm.Realm;

@Singleton
public class UserLocalDataSource implements UserDataSource {
    @Override
    public Flowable<List<Person>> getUserList() {
        try(Realm realm = Realm.getDefaultInstance()){
            List<Person> list = realm.where(Person.class)
                    .findAll();
            if(list != null){
                return Flowable.just(realm.copyFromRealm(list));
            }
            return Flowable.empty();
        }
    }

    @Override
    public boolean isUserExist(String id) {
        try(Realm realm = Realm.getDefaultInstance()){
            Person user = realm.where(Person.class)
                    .equalTo(Person.PERSONAL_ID, id)
                    .findFirst();
            return user != null;
        }
    }

    @Override
    public Completable deleteUser(Person person) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                realm.executeTransaction(realm1 -> {
                    realm1.where(Person.class)
                            .equalTo(Person.PERSONAL_ID, person.getPersonalId())
                            .findAll()
                            .deleteAllFromRealm();
                });
            }
        });
    }

    @Override
    public Completable createUser(String name, String id, Group group) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                Person person = new Person(id, name, group);
                realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(person));
            }
        });
    }

    @Override
    public Completable updateUser(Person person) {
        return Completable.fromAction(() ->{
            try(Realm realm = Realm.getDefaultInstance()){
                realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(person));
            }
        });
    }
}
