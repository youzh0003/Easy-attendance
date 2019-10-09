package com.zhiyong.easy_attendance.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject implements Parcelable {
    public static String PERSONAL_ID = "personalId";

    @PrimaryKey
    private String personalId;
    private String name;
    private Group group;

    public Person() {
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Person(String personalId, String name, Group group) {
        this.personalId = personalId;
        this.name = name;
        this.group = group;
    }

    protected Person(Parcel in) {
        personalId = in.readString();
        name = in.readString();
        group = in.readParcelable(Group.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(personalId);
        dest.writeString(name);
        dest.writeParcelable(group, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
