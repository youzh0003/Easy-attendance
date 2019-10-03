package com.zhiyong.easy_attendance.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Record extends RealmObject implements Parcelable {
    @PrimaryKey
    @SerializedName("recordedAt")
    private Long recordedAt; // Timestamp in seconds
    private String name;
    private String type; // In/Out etc

    public Record() {
    }

    public Record(Long recordedAt, String name, String type) {
        this.recordedAt = recordedAt;
        this.name = name;
        this.type = type;
    }

    public Long getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Long recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected Record(Parcel in) {
        if (in.readByte() == 0) {
            recordedAt = null;
        } else {
            recordedAt = in.readLong();
        }
        name = in.readString();
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
        dest.writeString(name);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
