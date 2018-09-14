package ru.sav.cloudclient.presenter;

import com.arellomobile.mvp.MvpPresenter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


abstract class BaseApiPresenter<T, V extends BaseApiView> extends MvpPresenter<V>
        implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onComplete() {
        getViewState().hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        getViewState().showError(e.getLocalizedMessage());
    }

    @Override
    public void onNext(T t) {

    }
}
