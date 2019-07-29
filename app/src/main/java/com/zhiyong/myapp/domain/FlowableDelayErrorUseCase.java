package com.zhiyong.myapp.domain;

import com.zhiyong.myapp.executor.PostExecutionThread;
import com.zhiyong.myapp.executor.ThreadExecutor;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableDelayErrorUseCase<T, Params> {

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    FlowableDelayErrorUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link FlowableUseCase}.
     */
    abstract Flowable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param subscriber {@link DisposableObserver} which will be listening to the observable build
     *                   by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params     Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableSubscriber<T> subscriber, Params params) {
        final Flowable<T> flowable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler(), true);
        addDisposable(flowable.subscribeWith(subscriber));


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
    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}

