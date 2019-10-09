package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase<Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    CompletableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link FlowableUseCase}.
     */
    abstract Completable buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param subscriber {@link DisposableCompletableObserver} which will be listening to the observable build
     *                   by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params     Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableCompletableObserver subscriber, Params params) {
        final Completable completable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(completable.subscribeWith(subscriber));


    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
