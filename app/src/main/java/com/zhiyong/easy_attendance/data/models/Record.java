package com.zhiyong.easy_attendance.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Record extends RealmObject implements Parcelable {
    public static String DATE = "date";
    public static String PERSON_GROUP_NAME = "person.group.name";
    @PrimaryKey
    @SerializedName("recordedAt")
    private Long recordedAt; // Timestamp in seconds
    private String date; // For filter
    private Person person;
    private String type; // In/Out etc

    public Record() {
    }

    public Record(Long recordedAt, String date, Person person, String type) {
        this.recordedAt = recordedAt;
        this.date = date;
        this.person = person;
        this.type = type;
    }

    protected Record(Parcel in) {
        if (in.readByte() == 0) {
            recordedAt = null;
        } else {
            recordedAt = in.readLong();
        }
        date = in.readString();
        person = in.readParcelable(Person.class.getClassLoader());
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (recordedAt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(recordedAt);
        }
        dest.writeString(date);
        dest.writeParcelable(person, flags);
        dest.writeString(type);
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public Long getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Long recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }
}
