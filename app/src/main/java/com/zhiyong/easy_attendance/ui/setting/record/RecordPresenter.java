package com.zhiyong.easy_attendance.ui.setting.record;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.domain.GetGroupsUseCase;
import com.zhiyong.easy_attendance.domain.GetRecordsQueryUseCase;
import com.zhiyong.easy_attendance.domain.GetRecordsUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class RecordPresenter extends BasePresenter<RecordMvpView> {
    private final GetRecordsUseCase getRecordsUseCase;
    private final GetRecordsQueryUseCase getRecordsQueryUseCase;
    private final GetGroupsUseCase getGroupsUseCase;
    @Inject
    public RecordPresenter(GetRecordsUseCase getRecordsUseCase,
                           GetRecordsQueryUseCase getRecordsQueryUseCase,
                           GetGroupsUseCase getGroupsUseCase) {
        this.getRecordsUseCase = getRecordsUseCase;
        this.getRecordsQueryUseCase = getRecordsQueryUseCase;
        this.getGroupsUseCase = getGroupsUseCase;
    }

    public void deleteRecord(Record record) {//TODO
    }

    public void getRecords() {
        getRecordsUseCase.execute(new DisposableSubscriber<List<Record>>() {
            @Override
            public void onNext(List<Record> records) {
                if(getMvpView() != null){
                    getMvpView().getRecordSuccess(records);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null){
                    getMvpView().getRecordsFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        },new GetRecordsUseCase.Params());
    }

    public void getRecords(String date, String groupName) {
        getRecordsQueryUseCase.execute(new DisposableSubscriber<List<Record>>() {
            @Override
            public void onNext(List<Record> recordList) {
                if(getMvpView() != null){
                    getMvpView().getRecordSuccess(recordList);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null){
                    getMvpView().getRecordsFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        }, new GetRecordsQueryUseCase.Params(date, groupName));
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

    @Override
    public void detachView() {
        super.detachView();
        getRecordsUseCase.dispose();
        getRecordsQueryUseCase.dispose();
        getGroupsUseCase.dispose();
    }
}
