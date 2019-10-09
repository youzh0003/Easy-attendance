package com.zhiyong.easy_attendance.ui.qr;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.domain.CreateRecordUseCase;
import com.zhiyong.easy_attendance.domain.GetUsersUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class QRCodeRecognitionPresenter extends BasePresenter<QRCodeRecognitionMvpView> {

    private final GetUsersUseCase getUsersUseCase;
    private final CreateRecordUseCase createRecordUseCase;
    @Inject
    QRCodeRecognitionPresenter(GetUsersUseCase getUsersUseCase,
                               CreateRecordUseCase createRecordUseCase) {

        this.getUsersUseCase = getUsersUseCase;
        this.createRecordUseCase = createRecordUseCase;
    }

    void getUsers() {
        getUsersUseCase.execute(new DisposableSubscriber<List<Person>>() {
            @Override
            public void onNext(List<Person> people) {
                if(getMvpView() != null){
                    getMvpView().getUsersSuccess(people);
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

    public void saveRecord(Record record) {
        createRecordUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().createRecordSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().createRecordFailed();
                }
            }
        }, new CreateRecordUseCase.Params(record));
    }

    @Override
    public void detachView() {
        super.detachView();
        getUsersUseCase.dispose();
        createRecordUseCase.dispose();
    }
}
